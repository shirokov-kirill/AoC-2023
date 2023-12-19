package solutions

import java.io.File
import java.lang.StringBuilder
import java.util.stream.IntStream.range

class Task1 {

    private val mapping = mapOf(
        "zero" to "0",
        "one" to "1",
        "two" to "2",
        "three" to "3",
        "four" to "4",
        "five" to "5",
        "six" to "6",
        "seven" to "7",
        "eight" to "8",
        "nine" to "9"
    )

    private fun matchWordsToDigits(string: String): String {
        val builder = StringBuilder()
        for (i in range(0, string.length)){
            for (key in mapping.keys){
                if(string.substring(i).startsWith(key)) {
                    builder.append(mapping[key])
                }
            }
            builder.append(string[i])
        }
        return builder.toString()
    }

    private fun getNumber(string: String): Int {
        val realString = matchWordsToDigits(string)
        var firstDigit = 0
        var lastDigit = 0
        for (char in realString) {
            if(char.isDigit()) {
                firstDigit = char.digitToInt()
                break
            }
        }
        for (char in realString.reversed()) {
            if(char.isDigit()) {
                lastDigit = char.digitToInt()
                break
            }
        }
        return firstDigit * 10 + lastDigit
    }

    fun process() {
        val file = File("src/main/kotlin/Solutions/inputs/input1.txt")
        var sum = 0
        for (line in file.readLines(Charsets.UTF_8)) {
            sum += getNumber(line)
        }
        println(sum)
    }
}