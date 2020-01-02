package ru.itis.entities

import org.json.JSONObject

/**
 * Покерные комбинации
 * Начинающим игрокам в покер в первую очередь нужно выучить покерные комбинации. Все они изображены на нижнем рисунке и по возрастанию располагаются так:

 * Старшая карта
 * Пара
 * Две пары
 * Сет
 * Стрит
 * Флеш
 * Фулл Хаус
 * Каре
 * Стрит Флеш
 * Роял Флеш
 *
 * Каждая комбинация создается из 2-х карт раздаваемых в закрытую каждому игроку
 * и 5 общих открытых карт выкладываемых в середину стола, т.е. используется 7 карт.
 * Таким образом вы видите только 5 карт, но не можете знать какие еще 2 карты есть у противника.
 * Поэтому, покер и называют игрой с неполной информацией.
 */

// TODO: more strict detection...
enum class GameCombination(val power: Int, val isDetected: (Array<Card>) -> Boolean) {
    HIGH_HAND(0, fun(cards: Array<Card>): Boolean {
        return true
    }),
    ONE_PAIR(1, fun(cards: Array<Card>): Boolean {
        val map = getMap(cards)
        val values = map.getJSONArray("values")
        val pairsAmount: Int = values.count { it == 2 }
        return pairsAmount >= 1
    }),
    TWO_PAIRS(2, fun(cards: Array<Card>): Boolean {
        val map = getMap(cards)
        val values = map.getJSONArray("values")
        val pairsAmount: Int = values.count { it == 2 }
        return pairsAmount >= 2
    }),
    THREE_OF_A_KIND(3, fun(cards: Array<Card>): Boolean {
        val map = getMap(cards)
        val values = map.getJSONArray("values")
        val setsAmount: Int = values.count { it == 3 }
        return setsAmount >= 1
    }),
    STRAIGHT(4, fun(cards: Array<Card>): Boolean {
        val map = getMap(cards)
        val values = map.getJSONArray("values")
        val stringified = values.map { it -> if (it as Int >= 1) 1 else 0 }.joinToString("")
        return stringified.contains("11111")
    }),
    FLUSH(5, fun(cards: Array<Card>): Boolean {
        val map = getMap(cards)
        val suits = map.getJSONArray("suits")
        return suits.count { it == 5 } >= 1
    }),
    FULL_HOUSE(6, fun(cards: Array<Card>): Boolean {
        val map = getMap(cards)
        val values = map.getJSONArray("values")
        val setsAmount: Int = values.count { it == 3 }
        val pairAmount: Int = values.count {it == 2}
        return setsAmount >= 1 && pairAmount >= 1
    }),
    FOUR_OF_A_KIND(7, fun(cards: Array<Card>): Boolean {
        val map = getMap(cards)
        val values = map.getJSONArray("values")
        val fourAmount: Int = values.count { it == 4 }
        return fourAmount >= 1
    }),
    STRAIGHT_FLUSH(8, fun(cards: Array<Card>): Boolean {
        // FIXME
        return STRAIGHT.isDetected(cards) && FLUSH.isDetected(cards)
    }),
    ROYAL_FLUSH(9, fun(cards: Array<Card>): Boolean {
        // FIXME
        return STRAIGHT.isDetected(cards) && FLUSH.isDetected(cards)
    });


    companion object {
        fun max(combinations: Array<GameCombination>): GameCombination {
            return combinations.maxBy { it.power }!!
        }
        fun getValues(cards: Array<Card>): List<Int> {
            return cards.map { it.value.value }
        }

        fun getSuits(cards: Array<Card>): List<String> {
            return cards.map { it.suit.name }
        }

        fun getMap(cards: Array<Card>): JSONObject {
            val _values = getValues(cards)
            val _suits = getSuits(cards)

            return JSONObject(object{
                val values = Value.values().map { v -> _values.count { cv -> cv == v.value}}
                val suits = Suit.values().map { s -> _suits.count { cs -> cs == s.name}}
            })
        }

        fun compute(cards: Array<Card>): GameCombination {
            var current = HIGH_HAND
            values().forEach {
                if (it.isDetected(cards) && it.power > current.power) {
                    current = it
                }
            }
            return current
        }

        fun getByPower(power: Int): GameCombination {
            return values().find { it.power == power }!!
        }

        fun getKicker(cards: Array<Card>): Card {
            return cards.maxBy { it.value }!!
        }

    }
}

fun test_high_hand(): Boolean {
    val cards = arrayOf(
        Card(Value.ACE, Suit.CLUBS),
        Card(Value.TWO, Suit.DIAMONDS),
        Card(Value.SEVEN, Suit.CLUBS),
        Card(Value.THREE, Suit.DIAMONDS),
        Card(Value.FIVE, Suit.CLUBS),
        Card(Value.NINE, Suit.SPADES),
        Card(Value.FOUR, Suit.CLUBS)
    )

    val equals = GameCombination.compute(cards) == GameCombination.HIGH_HAND
    println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> TEST:: HIGH_HAND -> $equals >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>")

    println(GameCombination.compute(cards))
    println(GameCombination.getValues(cards))
    println(GameCombination.getSuits(cards))
    println(GameCombination.getMap(cards))
    return equals
}

fun test_one_pair(): Boolean {
    val cards = arrayOf(
        Card(Value.ACE, Suit.CLUBS),
        Card(Value.TWO, Suit.DIAMONDS),
        Card(Value.THREE, Suit.CLUBS),
        Card(Value.THREE, Suit.DIAMONDS),
        Card(Value.FIVE, Suit.CLUBS),
        Card(Value.SIX, Suit.SPADES),
        Card(Value.FOUR, Suit.CLUBS)
    )

    val equals = GameCombination.compute(cards) == GameCombination.ONE_PAIR
    println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> TEST:: ONE_PAIR -> $equals >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>")

    println(GameCombination.compute(cards))
    println(GameCombination.getValues(cards))
    println(GameCombination.getSuits(cards))
    println(GameCombination.getMap(cards))
    return equals
}

fun test_two_pairs(): Boolean {
    val cards = arrayOf(
        Card(Value.ACE, Suit.CLUBS),
        Card(Value.TWO, Suit.DIAMONDS),
        Card(Value.THREE, Suit.CLUBS),
        Card(Value.THREE, Suit.DIAMONDS),
        Card(Value.FIVE, Suit.CLUBS),
        Card(Value.FOUR, Suit.SPADES),
        Card(Value.FOUR, Suit.CLUBS)
    )

    val equals = GameCombination.compute(cards) == GameCombination.TWO_PAIRS
    println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> TEST:: TWO_PAIRS -> $equals >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>")

    println(GameCombination.compute(cards))
    println(GameCombination.getValues(cards))
    println(GameCombination.getSuits(cards))
    println(GameCombination.getMap(cards))
    return equals
}

fun test_three_of_a_kind(): Boolean {
    val cards = arrayOf(
        Card(Value.ACE, Suit.CLUBS),
        Card(Value.THREE, Suit.CLUBS),
        Card(Value.THREE, Suit.SPADES),
        Card(Value.THREE, Suit.DIAMONDS),
        Card(Value.FIVE, Suit.SPADES),
        Card(Value.SEVEN, Suit.SPADES),
        Card(Value.SIX, Suit.CLUBS)
    )

    val equals = GameCombination.compute(cards) == GameCombination.THREE_OF_A_KIND
    println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> TEST:: THREE_OF_A_KIND -> $equals >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>")

    println(GameCombination.compute(cards))
    println(GameCombination.getValues(cards))
    println(GameCombination.getSuits(cards))
    println(GameCombination.getMap(cards))
    return equals
}

fun test_straight(): Boolean {
    val cards = arrayOf(
        Card(Value.ACE, Suit.DIAMONDS),
        Card(Value.TWO, Suit.DIAMONDS),
        Card(Value.THREE, Suit.DIAMONDS),
        Card(Value.FOUR, Suit.DIAMONDS),
        Card(Value.FIVE, Suit.DIAMONDS),
        Card(Value.SIX, Suit.SPADES),
        Card(Value.SIX, Suit.DIAMONDS)
    )

    val equals = GameCombination.compute(cards) == GameCombination.STRAIGHT
    println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> TEST:: STRAIGHT -> $equals >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>")

    println(GameCombination.compute(cards))
    println(GameCombination.getValues(cards))
    println(GameCombination.getSuits(cards))
    println(GameCombination.getMap(cards))
    return equals
}

fun test_flush(): Boolean {
    val cards = arrayOf(
        Card(Value.ACE, Suit.DIAMONDS),
        Card(Value.JACK, Suit.DIAMONDS),
        Card(Value.THREE, Suit.DIAMONDS),
        Card(Value.FOUR, Suit.DIAMONDS),
        Card(Value.FIVE, Suit.CLUBS),
        Card(Value.SIX, Suit.SPADES),
        Card(Value.TEN, Suit.DIAMONDS)
    )

    val equals = GameCombination.compute(cards) == GameCombination.FLUSH
    println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> TEST:: FLUSH -> $equals >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>")

    println(GameCombination.compute(cards))
    println(GameCombination.getValues(cards))
    println(GameCombination.getSuits(cards))
    println(GameCombination.getMap(cards))
    return equals
}

fun test_full_house(): Boolean {
    val cards = arrayOf(
        Card(Value.THREE, Suit.CLUBS),
        Card(Value.THREE, Suit.SPADES),
        Card(Value.THREE, Suit.DIAMONDS),
        Card(Value.FOUR, Suit.DIAMONDS),
        Card(Value.FIVE, Suit.CLUBS),
        Card(Value.FIVE, Suit.SPADES),
        Card(Value.TEN, Suit.DIAMONDS)
    )

    val equals = GameCombination.compute(cards) == GameCombination.FULL_HOUSE
    println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> TEST:: FULL_HOUSE -> $equals >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>")

    println(GameCombination.compute(cards))
    println(GameCombination.getValues(cards))
    println(GameCombination.getSuits(cards))
    println(GameCombination.getMap(cards))
    return equals
}

fun test_four_of_a_kind(): Boolean {
    val cards = arrayOf(
        Card(Value.ACE, Suit.DIAMONDS),
        Card(Value.TWO, Suit.DIAMONDS),
        Card(Value.THREE, Suit.DIAMONDS),
        Card(Value.SIX, Suit.HEARTS),
        Card(Value.SIX, Suit.SPADES),
        Card(Value.SIX, Suit.CLUBS),
        Card(Value.SIX, Suit.DIAMONDS)
    )

    val equals = GameCombination.compute(cards) == GameCombination.FOUR_OF_A_KIND
    println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> TEST:: FOUR_OF_A_KIND -> $equals >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>")

    println(GameCombination.compute(cards))
    println(GameCombination.getValues(cards))
    println(GameCombination.getSuits(cards))
    println(GameCombination.getMap(cards))
    return equals
}

fun test_straight_flush(): Boolean {
    val cards = arrayOf(
        Card(Value.ACE, Suit.HEARTS),
        Card(Value.TWO, Suit.DIAMONDS),
        Card(Value.THREE, Suit.DIAMONDS),
        Card(Value.FOUR, Suit.DIAMONDS),
        Card(Value.FIVE, Suit.DIAMONDS),
        Card(Value.SIX, Suit.DIAMONDS),
        Card(Value.SIX, Suit.HEARTS)
    )

    val equals = GameCombination.compute(cards) == GameCombination.STRAIGHT_FLUSH
    println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> TEST:: STRAIGHT_FLUSH -> $equals >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>")

    println(GameCombination.compute(cards))
    println(GameCombination.getValues(cards))
    println(GameCombination.getSuits(cards))
    println(GameCombination.getMap(cards))
    return equals
}

fun test_royal_flush(): Boolean {
    val cards = arrayOf(
        Card(Value.ACE, Suit.DIAMONDS),
        Card(Value.TWO, Suit.DIAMONDS),
        Card(Value.TEN, Suit.DIAMONDS),
        Card(Value.JACK, Suit.DIAMONDS),
        Card(Value.QUEEN, Suit.DIAMONDS),
        Card(Value.KING, Suit.DIAMONDS),
        Card(Value.ACE, Suit.DIAMONDS)
    )

    val equals = GameCombination.compute(cards) == GameCombination.ROYAL_FLUSH
    println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> TEST:: ROYAL_FLASH -> $equals >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>")

    println(GameCombination.compute(cards))
    println(GameCombination.getValues(cards))
    println(GameCombination.getSuits(cards))
    println(GameCombination.getMap(cards))
    return equals
}

fun runTests() {
    test_high_hand()
    test_one_pair()
    test_two_pairs()
    test_three_of_a_kind()
    test_straight()
    test_flush()
    test_full_house()
    test_four_of_a_kind()
    test_straight_flush()
    test_royal_flush()
}
fun main() {
//    GameCombination.values().forEachIndexed { i, combination ->
//        println("$i. $combination")
//    }
    runTests()
}