package solutions

import java.io.File
import kotlin.math.min

class Task13 {
    fun process() {
        val file = File("src/main/kotlin/Solutions/inputs/input13.txt")
        val puzzles = file.readLines(Charsets.UTF_8)
            .joinToString("\n")
            .split("\n\n")
        println(puzzles.sumOf { findReflectionNumber(it) })
    }

    private fun findReflectionNumber(puzzle: String): Int {
        val rows = puzzle.split("\n")
        val columns = puzzle.split("\n").first().indices. map {
            rows.map {it1 ->
                it1[it]
            }.joinToString("")
        }
        val horizontalIndex = rows.indices.firstOrNull {
            val mirrorAt = it + 1
            val rangeLimit = min(mirrorAt, rows.size - mirrorAt)
            isReflection(rows.subList(mirrorAt - rangeLimit, mirrorAt), rows.subList(mirrorAt, mirrorAt + rangeLimit))
        } ?: -1
        val verticalIndex = columns.indices.firstOrNull() {
            val mirrorAt = it + 1
            val rangeLimit = min(mirrorAt, columns.size - mirrorAt)
            isReflection(columns.subList(mirrorAt - rangeLimit, mirrorAt), columns.subList(mirrorAt, mirrorAt + rangeLimit))
        } ?: -1

        return (verticalIndex + 1) + 100 * (horizontalIndex + 1)
    }

    private fun isReflection(left: List<String>, right: List<String>): Boolean {
        check(left.size == right.size)
        var isReflection = left.isNotEmpty()
        var isFirstMistake = true
        for (i in left.indices) {
            for (j in left[i].indices) {
                val comparisonResult = left[i][j] == right[right.size - 1 - i][j]
                isReflection = isReflection && (comparisonResult || isFirstMistake)
                if(!comparisonResult){
                    isFirstMistake = false
                }
            }
        }
        return isReflection && !isFirstMistake
    }
}