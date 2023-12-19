package solutions

import java.io.File

class Task3 {
    private class Position(val line: Int, val start: Int, val length: Int) {
        fun isAdjacent(gearPosition: Position): Boolean =
            gearPosition.line in line - 1..line + 1 && gearPosition.start in start - 1..start + length
    }

    private fun findGears(field: List<String>): List<Position> {
        val positions = mutableListOf<Position>()
        for ((i, line) in field.withIndex()) {
            for((j, ch) in line.withIndex()) {
                if(ch == '*') {
                    positions.add(Position(i, j, 1))
                }
            }
        }
        return positions
    }

    private fun findNumbers(field: List<String>): List<Pair<Position, Int>> {
        val result = mutableListOf<Pair<Position, Int>>()
        for ((j, line) in field.withIndex()) {
            var inDigit = false
            var startIndex = 0
            for((i, ch) in line.withIndex()) {
                if(!ch.isDigit() && inDigit) {
                    inDigit = false
                    result.add(Pair(Position(j, startIndex, i - startIndex), line.substring(startIndex, i).toInt()))
                    continue
                } else if(ch.isDigit() && !inDigit) {
                    // beginning of number
                    inDigit = true
                    startIndex = i
                } else {
                    continue
                }
            }
            if(inDigit) {
                result.add(Pair(Position(j, startIndex, line.length - startIndex), line.substring(startIndex, line.length).toInt()))
                continue
            }
        }
        return result
    }

    private fun runAlgorithm(field: List<String>): Int {
        val numbersAdjacent = mutableListOf<Pair<Int, Int>>()
        val numbers = findNumbers(field)
        val gears = findGears(field)
        outer@for ((i, numberStruct1) in numbers.withIndex()) {
            for(gear in gears) {
                if(numberStruct1.first.isAdjacent(gear)) {
                    // found a pair
                    for (j in i + 1 until numbers.size) {
                        if(numbers[j].first.isAdjacent(gear)) {
                            // found second number
                            for (k in j + 1 until numbers.size) {
                                if(numbers[k].first.isAdjacent(gear)) {
                                    // adjacent to more than two numbers
                                    continue@outer
                                }
                            }
                            numbersAdjacent.add(Pair(numberStruct1.second, numbers[j].second))
                            continue@outer
                        }
                    }
                }
            }
        }
        return numbersAdjacent.sumOf { it.first * it.second }
    }

    fun process() {
        val file = File("src/main/kotlin/Solutions/inputs/input3.txt")
        val field = mutableListOf<String>()
        for (line in file.readLines(Charsets.UTF_8)) {
            field.add(line)
        }
        println(runAlgorithm(field))
    }
}