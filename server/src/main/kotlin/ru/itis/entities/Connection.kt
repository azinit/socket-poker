package ru.itis.entities

import org.json.JSONObject
import ru.itis.services.CardService
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.io.PrintWriter
import java.lang.Exception
import java.net.Socket
import java.util.*
import kotlin.random.Random

//class ru.itis.entities.Connection(var server: ru.itis.entities.Server, var socket: Socket): Runnable {
//
//    override fun run() {
//
//    }
//}

private var connectionAmount = 0

class Connection(var server: Server, var socket: Socket) : Runnable {
    val EXIT_CODE = 100
    val DEFAULT_CODE = 400
    val SUCCESS_CODE = 200
    val INTERNAL_CODE = 500

    var thread: Thread
    var writer: PrintWriter
    var reader: BufferedReader
    var id: Int

    // state
    var myCards: Array<Card?>
    var money = 1000
    var isPlaying = true
    var isActive = false
    var isAccepted = false
    var isWinner = false
    var status = ""
    var bet = 0

    init {
        thread = Thread(this)
        thread.start()
        reader = BufferedReader(InputStreamReader(socket.getInputStream()))
        writer = PrintWriter(OutputStreamWriter(socket.getOutputStream()))
        myCards = Array(2) { null }
        id = ++connectionAmount
        isActive = id == 1
    }

    override fun run() { // Взаимодействие между клиентом и сервером
        while (true) {
            println("client$id...............................................................")
            // if not null...
            val request = JSONObject(reader.readLine())
            val response = dispatch(request)

            writer.println(response)
            writer.flush()

            if (response.get("code") == EXIT_CODE) {
                break
            }
//            writer.println("[CL$id] SERVER: Press enter to interact with ru.itis.entities.Server")
//            writer.flush()
        }
        println("Disconnected: client$id...............................................................")
    }

    private fun dispatch(request: JSONObject): JSONObject {
        val action: String = request.get("action") as String
//        val data = request.get("data") as JSONObject
        return when(action) {
//            "get_game_step" -> {
//                println("REQUEST : Get game state")
//                println("RESPONSE: $card")
//                JSONObject(object{
//                    val code = if (card !== null) SUCCESS_CODE else INTERNAL_CODE
//                    val data = object {
//                        val card = card
//                    }
//                })
//            }
            "show_common" -> {
                if (isActive) {
                    nextCommon()
                }


                JSONObject(object {
                    val code = if (isActive) SUCCESS_CODE else INTERNAL_CODE
                    val data = object{}
                })
            }
            "action_call" -> {
                if (isActive && !isCalled() && money + bet >= server.callSum) {
                    next(this)
                    isAccepted = true
                    betSum(server.callSum - bet)
                    status = "Call ${server.callSum}"
                }

                println("REQUEST : CALL: 100")
                println("RESPONSE: ${if (isActive && isCalled()) SUCCESS_CODE else INTERNAL_CODE}")

                JSONObject(object {
                    val code = if (isActive && isCalled()) SUCCESS_CODE else INTERNAL_CODE
                    val data = object{}
                })
            }
            "action_bet" -> {
                // TODO: custom amount
                if (isActive && money + bet >= server.callSum + 100) {
                    next(this)
                    // TODO: add sum constraint
                    // TODO: vabank
                    betSum(server.callSum - bet)
                    if (isCalled()) {
                        betSum(100)
                    }
                    server.callSum += 100
                    status = "Bet: ${server.callSum}"
                }

                println("REQUEST : BET: 100")
                println("RESPONSE: ${if (isActive && isCalled()) SUCCESS_CODE else INTERNAL_CODE}")

                JSONObject(object {
                    val code = if (isActive && isCalled()) SUCCESS_CODE else INTERNAL_CODE
                    val data = object{}
                })
            }
            // nextActive...
            "action_fold" -> {
                if (isActive) {
                    next(this)
                    isPlaying = false
                    status = "Fold"

                    if (isRemainedOnePlaying()) {
                        val winner = server.connections.find { it.isPlaying }!!
                        winner.isWinner = true
                        winner.money += server.totalSum
                        server.totalSum = 0

                        resetGame()
                    }
                }


                println("REQUEST : FOLD")
                println("RESPONSE: ${if (isActive) SUCCESS_CODE else INTERNAL_CODE}")

                JSONObject(object {
                    val code = if (isActive) SUCCESS_CODE else INTERNAL_CODE
                    val data = object{}
                })
            }
            "action_check" -> {
                if (isActive && isCalled()) {
                    next(this)
                    isAccepted = true
                    status = "Check"

                    // GameLogic
                    if (areAcceptedAll()) {
                        resetStep()
                        // Preflop
                        when (server.step) {
                            GameStep.PREFLOP -> {
                                nextCommon()
                                nextCommon()
                                nextCommon()

                                println("....................................................")
                                println("Step: -->> FLOP")
                                println(server.common.map { it.toString() })
                                println("....................................................")

                                server.step = GameStep.FLOP
                            }
                            GameStep.FLOP -> {
                                nextCommon()

                                println("....................................................")
                                println("Step: -->> TORN")
                                println(server.common.map { it.toString() })
                                println("....................................................")
                                server.step = GameStep.TORN
                            }
                            GameStep.TORN -> {
                                nextCommon()

                                println("....................................................")
                                println("Step: -->> RIVER")
                                println(server.common.map { it.toString() })
                                println("....................................................")
                                server.step = GameStep.RIVER
                            }
                            GameStep.RIVER -> {
                                detectWinner()

                                println("....................................................")
                                println("Step: -->> SHOWN_DOWN")
                                println(server.common.map { it.toString() })
                                println("....................................................")
                                server.step = GameStep.SHOWN_DOWN
                            }
                            GameStep.SHOWN_DOWN -> {
                                server.winners().forEach { it.isWinner = false }
                                resetGame()

                                println("....................................................")
                                println("Step: -->> PREFLOP")
                                println(server.common.map { it.toString() })
                                println("....................................................")
                            }
                        }
                    }

                }


                println("REQUEST : CHECK")
                println("RESPONSE: ${if (isActive) SUCCESS_CODE else INTERNAL_CODE}")

                JSONObject(object {
                    val code = if (isActive) SUCCESS_CODE else INTERNAL_CODE
                    val data = object{}
                })
            }
            "get_data" -> {
                val self: Connection = this
                JSONObject(object {
                    val code = SUCCESS_CODE
                    val data = getInfo(self)
                })
            }
            "get_total_data" -> {
                val self: Connection = this
                JSONObject(object{
                    val code = SUCCESS_CODE
                    val data = object {
                        val round = server.gameRound
                        val step = server.step.name
                        val common = server.common.mapIndexed { _index, _card -> object {
                            val index = _index
                            val card = if (_card == null) "" else _card.serialize()
                        }}
                        val totalSum = server.totalSum
                        val clients = server.connections.map { getInfo(it) }
                    }
                })
            }
            "get_my_cards" -> {
                if (myCards[0] == null && myCards[1] == null) {
                    myCards[0] = CardService.getRandom()!!
                    myCards[1] = CardService.getRandom()!!
                }

                JSONObject(object{
                    val code = SUCCESS_CODE
                    val data = object {
                        val cards = myCards.map { it!!.serialize() }
                    }
                })
            }
            "take_new_cards" -> {
                var newCards: Array<Card?>
                try {
                    newCards = Array(2){CardService.getRandom()!!}
                } catch (e: Exception) {
                    newCards = Array(2) {null}
                }
                JSONObject(object{
                    val code = SUCCESS_CODE
                    val data = object {
                        val cards = newCards.map { it?.serialize() }
                    }
                })
            }
            "get_random_card" -> {
                val card = CardService.getRandom()?.serialize()
                println("REQUEST : Get random card")
                println("RESPONSE: $card")
                JSONObject(object{
                    val code = if (card !== null) SUCCESS_CODE else INTERNAL_CODE
                    val data = object {
                        val card = card
                    }
                })
            }
            "exit"      -> {
                println("REQUEST : <EXIT>")
                println("RESPONSE: <Terminate work...>")
                JSONObject(object{
                    val code = EXIT_CODE
                    val data = object{}
                })
            }
            else        -> {
                println("REQUEST : <Unknown operation: $action>")
                println("RESPONSE: <Not found...>")
                JSONObject(object{
                    val code = DEFAULT_CODE
                    val data = object{}
                })
            }
        }
    }

    fun betSum(value: Int) {
        bet += value
        server.totalSum += value
        money -= value
    }

    fun resetStep() {
        server.connections.forEach {
            it.isAccepted = false
            it.bet = 0
            it.status = ""
        }
        server.callSum = 0
    }

    fun resetGame() {
        server.step = GameStep.PREFLOP
        CardService.generateNewDeck()
        server.common = Array(5) { i -> null}
        server.connections.forEach {
            it.myCards[0] = CardService.getRandom()!!
            it.myCards[1] = CardService.getRandom()!!
            it.isPlaying = true
            it.isActive = it.id == 1
            it.isAccepted = false
            it.isWinner = false
            it.status = "" 
            it.bet = 0
        }
        server.callSum = 0
        server.gameRound++

    }
    fun isCalled(): Boolean {
        return bet == server.callSum
    }

    fun isRemainedOnePlaying(): Boolean {
        return server.connections.count {it.isPlaying} == 1
    }

    fun areAcceptedAll(): Boolean {
        return server.connections.all { it.isAccepted }
    }

    fun nextCommon() {
        val nextInd = server.common.indexOfFirst { it == null }
        server.common[nextInd] = CardService.getRandom()
    }
    fun next(client: Connection) {
        client.isActive = false
        getNext(client).isActive = true
    }

    fun detectWinner() {
        val data: winnerData = Detection.detectWinners(server.connections)
        val winners = data.winners
        val winCombination = data.combination

        val share = server.totalSum / winners.size
        winners.forEachIndexed { i, winner ->
            winner.isWinner = true
            winner.money += share
            winner.status = "WINS: $winCombination"
        }
        server.totalSum = 0
    }
    fun getNext(client: Connection): Connection {
        val clients = server.connections
        var i = 1
        if (clients.count {it.isPlaying} > 1) {
            while (true) {
                var nextId = client.id + i
                if (nextId == clients.size + 1) { nextId = 1 }
                val nextClient = clients[nextId - 1]
                if (nextClient.isPlaying) {  return nextClient  }
                i++
            }
        }
        return client
    }
    companion object {
        fun getInfo(connection: Connection): Any {
            return object {
                val id = connection.id
                val money = connection.money
                val isPlaying = connection.isPlaying
                val isActive = connection.isActive 
                val isCalled = connection.isCalled()
                val isAccepted = connection.isAccepted
                val isWinner = connection.isWinner
                val myCards = connection.myCards.mapIndexed { _index, _card -> object {
                    val index = _index
                    val card = if (_card == null) "" else _card.serialize()
                }}
                val bet = connection.bet
                val status = connection.status
            }
        }
    }
}