package solutions

import java.io.File
import kotlin.math.min

class Task4 {
    private var cardCounts: MutableList<Int> = mutableListOf()

    private fun checkCard(input: String, index: Int) {
        var sum = 0
        val data = input.split(":", "|")
            .drop(1)
        val successSet = data[0].split(" ")
            .filter { it.isNotEmpty() }
            .map { it.toInt() }
            .toSet()
        data[1].split(" ")
            .filter { it.isNotEmpty() }
            .map { it.toInt() }
            .forEach {
                if(successSet.contains(it)) {
                    sum += 1
                }
            }
        for(i in index + 1 until min(cardCounts.size, index + 1 + sum)) {
            cardCounts[i] += 1 * cardCounts[index]
        }
    }

    fun process() {
        val file = File("src/main/kotlin/Solutions/inputs/input4.txt")
        val lines = file.readLines(Charsets.UTF_8)
        cardCounts = lines.indices.map { 1 }.toMutableList()
        for((i, line) in lines.withIndex()) {
            checkCard(line, i)
        }

        println(cardCounts.sum())
    }
}