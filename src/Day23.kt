import org.junit.jupiter.api.Test
import java.io.File

class Day23 {
    private val instructions = File(this.javaClass.getResource("23.txt").path).readLines().map { s ->
        val i = s.substring(0, 3)
        var r = s.substring(4)
        var c: Int? = null
        when (i) {
            "jie", "jio" -> {
                val p = r.split(", ")
                r = p[0]
                c = p[1].toInt()
            }
        }
        Triple(i, r, c)
    }

    private fun run(a: Int = 0, b: Int = 0): Int {
        val registers = mutableMapOf("a" to a, "b" to b)
        var i = 0
        loop@ while (i < instructions.size) {
            val (o, r, v) = instructions[i]
            when (o) {
                "hlf" -> registers[r] = registers[r]!! / 2
                "tpl" -> registers[r] = registers[r]!! * 3
                "inc" -> registers[r] = registers[r]!! + 1
                "jmp" -> {
                    i += r.toInt()
                    continue@loop
                }
                "jie" -> {
                    if (registers[r]!!.toUInt() % 2u == 0u) {
                        i += v!!
                        continue@loop
                    }
                }
                "jio" -> {
                    if (registers[r]!! == 1) {
                        i += v!!
                        continue@loop
                    }
                }
            }
            i++
        }
        return registers["b"]!!
    }

    @Test
    fun part1() {
        println(run())
    }

    @Test
    fun part2() {
        println(run(a = 1))
    }

}
