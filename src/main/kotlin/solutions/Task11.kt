package solutions

import java.io.File
import kotlin.math.abs

class Task11 {

    data class Galaxy(val x: Int, val y: Int)

    fun process() {
        val file = File("src/main/kotlin/Solutions/inputs/input11.txt")
        val space = file.readLines(Charsets.UTF_8)
        println(calculatePaths(space))
    }

    private fun expand(space: List<String>): List<String> {
        if(space.isEmpty()) {
            return space
        }
        val verticalExpansion = space.map {
            if(!it.contains(GALAXY)) {
                EXPANSION.toString().repeat(it.length)
            }else {
                it
            }
        }
        return verticalExpansion[0].indices.map {
            val column = verticalExpansion.indices.map {it1 -> verticalExpansion[it1][it] }.joinToString("")
            if(!column.contains(GALAXY)) {
                EXPANSION.toString().repeat(verticalExpansion[0].length)
            } else {
                column
            }
        }
    }

    private fun findGalaxies(space: List<String>): List<Galaxy> {
        val galaxies = mutableListOf<Galaxy>()
        for(i in space.indices) {
            for(j in space[i].indices) {
                if(space[i][j] == GALAXY) {
                    val expansionCountHorizontal = (0 until i)
                        .map { it1 -> space[it1][j] }
                        .count { it == EXPANSION }
                    val expansionCountVertical = space[i]
                        .substring(0, j + 1)
                        .count { it == EXPANSION }
                    galaxies.add(Galaxy(i - expansionCountHorizontal + EXPANSION_SIZE * expansionCountHorizontal, j - expansionCountVertical + EXPANSION_SIZE * expansionCountVertical))
                }
            }
        }
        return galaxies
    }

    private fun calculatePaths(space: List<String>): Long {
        val expandedSpace = expand(space)
        val galaxies = findGalaxies(expandedSpace)
        val distances = mutableListOf<Long>()
        for (i in galaxies.indices) {
            for(j in i + 1 until galaxies.size) {
                distances.add(abs(galaxies[i].x - galaxies[j].x).toLong() + abs(galaxies[i].y - galaxies[j].y))
            }
        }
        return distances.sum()
    }

    companion object {
        private const val GALAXY = '#'
        private const val EXPANSION = '%'
        private const val EXPANSION_SIZE = 1000000
    }
}