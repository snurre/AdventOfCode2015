import org.junit.jupiter.api.Test
import java.io.File

class Day08 {

    private val data = File(this.javaClass.getResource("08.txt").path).readLines()

    @Test
    fun part1() {
        var codeSize = 0
        var memorySize = 0
        data.forEach { s ->
            codeSize += s.length
            val trimmed = s.substring(1, s.length - 1)
            var i = 0
            var escaped = 0
            while (i < trimmed.length) {
                if (trimmed[i] == '\\') {
                    when (trimmed[i + 1]) {
                        '\\' -> {
                            escaped++
                            i++
                        }
                        '"' -> {
                            escaped++
                            i++
                        }
                        'x' -> {
                            escaped += 3
                            i += 3
                        }
                    }
                }
                i++
            }
            memorySize += trimmed.length - escaped
        }
        println("$codeSize - $memorySize = ${codeSize - memorySize}")
    }

    @Test
    fun part2() {
        var oldCodeSize = 0
        var newCodeSize = 0
        val escape = '\\'
        data.forEach { s ->
            val sb = StringBuilder().append("\"")
            s.forEach { c ->
                when (c) {
                    '\\' -> sb.append(escape)
                    '"' -> sb.append(escape)
                }
                sb.append(c)
            }
            sb.append("\"")
            val escaped = sb.toString()
            oldCodeSize += s.length
            newCodeSize += escaped.length
        }
        println("$newCodeSize - $oldCodeSize = ${newCodeSize - oldCodeSize}")
    }
}
