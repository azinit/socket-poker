package ru.itis.entities

import java.io.IOException
import java.net.ServerSocket
import java.util.*


class Server {
    val PORT = 8080
    val PLAYER_LIMIT = 6
    var connections: ArrayList<Connection>
    var totalSum = 0
    var common = Array<Card?>(5) {i -> null}
    var step = GameStep.PREFLOP
    var callSum = 0
    var gameRound = 1
//    var winners = Array<Connection?>( PLAYER_LIMIT ) {i -> null}

    init {
        println("ru.itis.entities.Server has been started on port 8080...")
        connections = ArrayList()
        run()
    }

    @Throws(IOException::class)
    fun run() {
        val s1 = ServerSocket(PORT)
        var amount = 0
        while (amount < PLAYER_LIMIT) {
            val client = s1.accept()
            client.tcpNoDelay = true
            println("Accepted $client")
            connections.add(Connection(this, client))
            amount++;
        }


    }

    fun winners(): List<Connection> {
        return connections.filter { it.isWinner }
    }

//    companion object {
//        // если вдруг у нас несколько серверов
//        @Throws(IOException::class)
//        @JvmStatic
//        fun ru.itis.demo.main(args: Array<String>) {
//            val server = ru.itis.entities.Server()
//        }
//    }
}

// если вдруг у нас несколько серверов
@Throws(IOException::class)
fun main(args: Array<String>) {
    val server = Server()
}