package solutions

import java.io.File

class Task2{

    private fun matchGameAttempt(input: String): Array<Int> {
        val items = input.split(" ", ",").filter { it.isNotEmpty() }
        val result = Array(3) { 0 }
        for (i in items.indices step 2) {
            when(items[i + 1]) {
                "red" -> result[0] = items[i].toInt()
                "green" -> result[1] = items[i].toInt()
                "blue" -> result[2] = items[i].toInt()
            }
        }
        return result
    }

    private fun getMinimumSet(results: List<Array<Int>>): Array<Int> {
        var minRed = 0
        var minGreen = 0
        var minBlue = 0
        results.forEach {
            minRed = maxOf(minRed, it[0])
            minGreen = maxOf(minGreen, it[1])
            minBlue = maxOf(minBlue, it[2])
        }
        return arrayOf(minRed, minGreen, minBlue)
    }

    private fun runGame(input: String): Int {
        val gameResults = mutableListOf<Array<Int>>()
        input.split(";", ":").drop(1).forEach {
            gameResults.add(matchGameAttempt(it))
        }
        val minimumSet = getMinimumSet(gameResults)
        return minimumSet[0] * minimumSet[1] * minimumSet[2]
    }

    fun process() {
        val file = File("src/main/kotlin/Solutions/inputs/input2.txt")
        var sumPossible = 0
        for (line in file.readLines(Charsets.UTF_8)) {
            sumPossible += runGame(line)
        }
        println(sumPossible)
    }
}