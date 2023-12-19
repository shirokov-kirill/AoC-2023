package solutions

import java.io.File

class Task8 {

    data class State(val name: String, val right: String, val left: String)

    fun process() {
        val file = File("src/main/kotlin/Solutions/inputs/input8.txt")
        val lines = file.readLines(Charsets.UTF_8)
        println(getNumberOfSteps(lines))
    }

    private fun getStates(input: List<String>): Map<String, State> =
        input.drop(2)
            .map { it.split(" ", "=", "(", ")", ",").filter { it1 -> it1.isNotEmpty() } }
            .associate { Pair(it[0], State(it[0], it[2], it[1])) }

    private fun getNumberOfSteps(input: List<String>): Long {
        val instructions = input.first()
        val states = getStates(input)
        val startStates = states.values.filter { it.name.endsWith("A") }
        val results = startStates.map {
            var currentState = it
            var steps = 0
            while (!currentState.name.endsWith("Z")) {
                currentState = when (instructions[steps % instructions.length]) {
                        'L' -> states[currentState.left]!!
                        else -> states[currentState.right]!!
                    }
                steps++
            }
            steps
        }.map { it.toLong() }
        return results.reduce { acc, l -> findLCM(acc, l) }
    }

    private fun findLCM(a: Long, b: Long): Long {
        val larger = if (a > b) a else b
        val maxLcm = a * b
        var lcm = larger
        while (lcm <= maxLcm) {
            if (lcm % a == 0L && lcm % b == 0L) {
                return lcm
            }
            lcm += larger
        }
        return maxLcm
    }
}