package ru.itis.entities

import org.json.JSONArray
import java.util.*
import kotlin.random.Random

class Deck {
    val MAX_RANGE_AMOUNT = 13
    var clubs: Array<Boolean> = newRange()
    var diamonds: Array<Boolean> = newRange()
    var hearts: Array<Boolean> = newRange()
    var spades: Array<Boolean> = newRange()

    fun reset() {
        clubs = newRange()
        diamonds = newRange()
        hearts = newRange()
        spades = newRange()
    }

    private fun newRange(): Array<Boolean> {
        return Array(MAX_RANGE_AMOUNT) { i -> true }
    }

    fun getSuit(suit: String): Array<Boolean> {
        return when (suit) {
            "clubs" -> clubs
            "diamonds" -> diamonds
            "hearts" -> hearts
            "spades" -> spades
            // КОСТЫЛЬ!
            else -> arrayOf(false)
        }
    }

    fun take(suit: String, value: Int) {
        when (suit) {
            "clubs" -> clubs[value] = false
            "diamonds" -> diamonds[value] = false
            "hearts" -> hearts[value] = false
            "spades" -> spades[value] = false
        }
    }

    fun isAnyRemained(suit: String): Boolean {
        return getSuit(suit).any { it }
    }

    fun isDeckEmpty(): Boolean {
        return !isAnyRemained("clubs") && !isAnyRemained("diamonds") && !isAnyRemained("hearts") && !isAnyRemained("spades")
    }

    fun remained(suit: String): Int {
        return getSuit(suit).count { it }
    }

    fun remained(): Int {
        return remained("clubs") + remained("diamonds") + remained("hearts") + remained("spades")
    }

    fun remainedIndices(suit: String): List<Int> {
        return getSuit(suit).mapIndexed { i, state -> if (state) i else -1 }.filter { it != -1 }
    }

    fun getRandom(): Card? {
        if (!isDeckEmpty()) {
            // Select suit
            var suit: String;
            while (true) {
                suit = listOf("clubs", "diamonds", "hearts", "spades").random()
                if (isAnyRemained(suit)) {
                    break;
                }
            }
            // Select value
            val value = remainedIndices(suit).random()
            take(suit, value)
//            while (true) {
//                value = Random.nextInt(0, MAX_RANGE_AMOUNT)
//                if (values[value]) {
//                    take(suit, value)
//                    break
//                }
//            }

            return Card.new(value + 1, suit)
        }
        return null
    }
}

fun main() {
    val deck = Deck()
    println(deck.clubs.contentToString())
    println(deck.getSuit("clubs").contentToString())
    for (i in 1..52) {
        println("$i. ${deck.getRandom()} ${deck.remained()}/${deck.MAX_RANGE_AMOUNT * 4}")
    }
//    deck.javaClass.getField("clubs")
}