package solutions

import java.io.File

class Task7 {
    fun process() {
        val file = File("src/main/kotlin/Solutions/inputs/input6.txt")
        val lines = file.readLines(Charsets.UTF_8)
        println(calculate(lines))
    }
}