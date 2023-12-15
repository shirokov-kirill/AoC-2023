package solutions

import java.io.File

class Task7 {

    private val comparator = object: Comparator<Pair<String, Int>> {
        private val FIVE_OF_A_KIND = 32
        private val FOUR_OF_A_KIND = 16
        private val FULL_HOUSE = 8
        private val THREE_OF_A_KIND = 4
        private val TWO_PAIR = 2
        private val ONE_PAIR = 1
        private val HIGH_ORDER = 0

        private val cardStrength = mutableMapOf(
            'J' to 0,
            '2' to 1,
            '3' to 2,
            '4' to 3,
            '5' to 4,
            '6' to 5,
            '7' to 6,
            '8' to 7,
            '9' to 8,
            'T' to 9,
            'Q' to 10,
            'K' to 11,
            'A' to 12
        )

        private fun getCombinationType(combination: String): Int {
            val characterMap = mutableMapOf<Char, Int>()
            cardStrength.keys
                .forEach { characterMap[it] = combination.count {it1 -> it1 == it }}
            val notNulls = characterMap.entries
                .map { it.value }
                .filter { it != 0 }
                .toList()
            return if(notNulls.size == 1) {
                FIVE_OF_A_KIND
            } else if(notNulls.size == 2 && notNulls.contains(4)) {
                FOUR_OF_A_KIND
            } else if(notNulls.size == 2 && notNulls.contains(3)) {
                FULL_HOUSE
            } else if(notNulls.size == 3 && notNulls.contains(3)) {
                THREE_OF_A_KIND
            } else if(notNulls.size == 3 && notNulls.contains(2) && notNulls.contains(1)) {
                TWO_PAIR
            } else if(notNulls.size == 4 && notNulls.contains(2)) {
                ONE_PAIR
            } else {
                HIGH_ORDER
            }
        }

        private fun modifyCombination(combination: String): String {
            val characterMap = mutableMapOf<Char, Int>()
            cardStrength.keys.filter { it1 -> it1 != 'J' }
                .forEach { it1 -> characterMap[it1] = combination.count {it2 -> it2 == it1 }}
            val maxCharacter = characterMap.entries.first { it1 -> it1.value == characterMap.values.max() }.key
            return combination.replace('J', maxCharacter)
        }

        override fun compare(o1: Pair<String, Int>?, o2: Pair<String, Int>?): Int {
            val value1 = o1?.first
            val value2 = o2?.first
            if(value1 == value2) {
                return 0
            } else if(value1 == null) {
                return -1
            } else if(value2 == null) {
                return 1
            }
            val modifiedCombination1 = modifyCombination(value1)
            val modifiedCombination2 = modifyCombination(value2)
            return if(getCombinationType(modifiedCombination1) == getCombinationType(modifiedCombination2)) {
                var index = 0
                while (cardStrength[value1[index]]!! - cardStrength[value2[index]]!! == 0) {
                    index++
                }
                return cardStrength[value1[index]]!! - cardStrength[value2[index]]!!
            } else {
                getCombinationType(modifiedCombination1) - getCombinationType(modifiedCombination2)
            }
        }
    }

    fun process() {
        val file = File("src/main/kotlin/Solutions/inputs/input7.txt")
        val lines = file.readLines(Charsets.UTF_8)
        println(playGame(lines))
    }

    private fun playGame(cardsCollections: List<String>): Long =
        cardsCollections.map {
            val data = it.split(" ")
            Pair(data[0], data[1].toInt())
        }.sortedWith(comparator)
            .mapIndexed { index, pair -> (index.toLong() + 1L) * pair.second.toLong() }
            .sum()
}