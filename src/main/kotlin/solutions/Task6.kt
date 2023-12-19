package solutions

import java.io.File

class Task6 {
    fun process() {
        val file = File("src/main/kotlin/Solutions/inputs/input6.txt")
        val lines = file.readLines(Charsets.UTF_8)
        println(calculate(lines))
    }

    private fun getLowest(maximumTime: Long, maxDistance: Long): Long {
        return generateSequence(0L) { it.inc() }
            .first {it * (maximumTime - it) > maxDistance }
    }

    private fun getHighest(maximumTime: Long, maxDistance: Long): Long {
        return generateSequence(maximumTime) { it.dec() }
            .first { (it - 1) * (maximumTime - it + 1) > maxDistance }
    }

    private fun calculate(input: List<String>): Long {
        val times = input
            .first()
            .split(" ").filter { it.isNotEmpty() }
            .drop(1).joinToString("").toLong()
        val distances = input
            .drop(1)
            .first()
            .split(" ").filter { it.isNotEmpty() }
            .drop(1).joinToString("").toLong()
        return getHighest(times, distances) - getLowest(times, distances)
    }
}