package ru.itis.entities

object Detection {
    // TODO: FULL_HOUSE_1 > FULL_HOUSE_2
    // ~ TODO: impl final all combinations!
    // V Action <-> Combination : Label
    // V if few finners: share totalSum
    // TODO: ACE as 1 and as KING + 1
    fun detectWinners(players: ArrayList<Connection>): winnerData {
        val combinations = players.map { it ->
            val cards: Array<Card> = it.myCards.filterNotNull().toTypedArray() + it.server.common.filterNotNull().toTypedArray()
//            val kicker: Card = GameCombination.getKicker(cards)
            GameCombination.compute(cards)
        }.toTypedArray()

        val winCombination = GameCombination.max(combinations)
        val indices = combinations.mapIndexed { i, combination -> if (combination == winCombination) i else null }.filterNotNull()
        return object : winnerData {
            override val winners = indices.map { i -> players[i] }.toTypedArray()
            override val combination = winCombination
        }
    }
}

interface winnerData {
    val winners: Array<Connection>
    val combination: GameCombination
}