package solutions

import java.io.File

class Task5 {

    data class Range(val source: Long, val destination: Long, val length: Long) {
        val range = source until source + length

        fun destination(value: Long): Long {
            val append = value - source
            return destination + append
        }
    }

    private fun parseInputForSeeds(input: String): List<Long> = input.split("\n")
            .first()
            .removePrefix("seeds: ")
            .split(" ")
            .map {
                it.toLong()
            }

    private fun parseInputForTables(input: String) = input
            .split("\n\n")
            .drop(1)
            .map {
                it
                    .split("\n")
                    .drop(1)
                    .map {it1 ->
                        it1
                            .split(" ")
                            .map { it2 ->
                                it2.toLong()
                            }
                    }
                    .map { it1 ->
                        Range(it1[1], it1[0], it1[2])
                    }
            }

    fun process() {
        val file = File("src/main/kotlin/Solutions/inputs/input5.txt")
        val lines = file.readLines(Charsets.UTF_8).joinToString("\n")
        val seeds = parseInputForSeeds(lines)
        val tables = parseInputForTables(lines)
        println(calculate(seeds, tables))
    }

    private fun calculate(seeds: List<Long>, tables: List<List<Range>>): Long {
        val seedRanges = seeds
            .windowed(2, 2)
            .map { it[0] .. it[0] + it[1] }

        val reversedMaps = tables.map {
            it.map {it1 ->
                Range(it1.destination, it1.source, it1.length)
            }
        }.reversed()

        return generateSequence(0L) {
            it.inc()
        }.first {
                val seed = reversedMaps.fold(it) { acc, map ->
                    map.find {it1 -> acc in it1.range }?.destination(acc) ?: acc
                }
                seedRanges.any { seedRange -> seed in seedRange }
            }
    }
}