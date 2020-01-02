package ru.itis.services

import org.json.JSONArray
import org.json.JSONObject
import ru.itis.entities.Card
import ru.itis.entities.Deck
import ru.itis.entities.Suit
import ru.itis.entities.Value
import kotlin.random.Random

object CardService {
    var deck = Deck()

    fun generateNewDeck() {
        deck.reset()
    }

    fun isAnyRemained(suit: String): Boolean {
        return deck.isAnyRemained(suit)
    }


    fun isDeckEmpty(): Boolean {
        return deck.isDeckEmpty()
    }

    fun remained(suit: String): Int {
        return deck.remained(suit)
    }

    fun remained(): Int {
        return deck.remained()
    }

    fun getRandom(): Card? {
        return deck.getRandom()
    }
}

fun main() {
    println(CardService.getRandom())
//    println(CardService.getRandom())
}