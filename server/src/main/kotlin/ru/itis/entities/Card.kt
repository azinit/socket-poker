package ru.itis.entities

import org.json.JSONObject

class Card internal constructor(val value: Value, val suit: Suit) {
    fun serialize(): String? {
        return serialize(this)
    }

    fun serialize(card: Card): String {
        return "${card.value.value}_${card.suit.name.toLowerCase()}";
    }

    fun deserialize(json: String): Card {
        val obj = json.split("_")
//        val obj = JSONObject(json)
        return new(
            obj[0].toInt(),
            obj[1]
        )
//        return Card(
//            Value.getByValue(obj[0].toInt()),
//            Suit.valueOf(suit.toUpperCase())
//
////            obj.get("value") as Value,
////            obj.get("suit") as Suit
//        )
    }

    override fun toString(): String {
        return "$suit$value"
    }

    companion object {
        fun new(value: Int, suit: String): Card {
            return Card(
                Value.getByValue(value),
                Suit.valueOf(suit.toUpperCase())
            )
        }
    }

}

enum class Value(val value: Int) {
    ACE(1),
    TWO(2),
    THREE(3),
    FOUR(4),
    FIVE(5),
    SIX(6),
    SEVEN(7),
    EIGHT(8),
    NINE(9),
    TEN(10),
    JACK(11),
    QUEEN(12),
    KING(13);

    override fun toString(): String {
        return when (this) {
            ACE -> "A"
            JACK -> "J"
            QUEEN -> "Q"
            KING -> "K"
            else -> this.value.toString()
        }
    }

    companion object {
        fun getByValue(value: Int): Value {
            return Value.values().find { it.value == value }!!
        }
    }
}

enum class Suit {
    CLUBS, DIAMONDS, HEARTS, SPADES;

    override fun toString(): String {
        return when (this) {
            CLUBS -> "♣"
            DIAMONDS -> "♦"
            HEARTS -> "♥"
            SPADES -> "♠"
        }
    }
}