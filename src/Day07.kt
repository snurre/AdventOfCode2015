import org.junit.jupiter.api.Test
import java.io.File
import kotlin.experimental.*

class Day07 {
    class Node(expr: () -> Short) {
        val value by lazy(expr::invoke)
    }

    private fun getCircuit(override: Pair<String, Short>? = null): Map<String, Node> {
        val wires = hashMapOf<String, Node>()

        fun p(s: String): Short = if (s.toShortOrNull() != null) s.toShort() else wires[s]!!.value

        fun o(s: String): (Short, Short) -> Short = when (s) {
            "AND" -> Short::and
            "OR" -> Short::or
            "LSHIFT" -> { a: Short, b: Short -> (a.toInt() shl b.toInt()).toShort() }
            "RSHIFT" -> { a: Short, b: Short -> (a.toInt() shr b.toInt()).toShort() }
            else -> {
                throw RuntimeException()
            }
        }

        File(this.javaClass.getResource("07.txt").path).useLines {
            it.forEach { s ->
                s.split(" ").let { parts ->
                    when {
                        parts.size == 3 -> wires[parts[2]] = Node { p(parts[0]) }
                        parts[0] == "NOT" -> wires[parts[3]] = Node { p(parts[1]).inv() }
                        else -> wires[parts[4]] = Node { o(parts[1]).invoke(p(parts[0]), p(parts[2])) }
                    }
                }
            }
        }

        if (override != null) {
            wires[override.first] = Node { override.second }
        }
        return wires
    }

    @Test
    fun part1() {
        val wires = getCircuit()
        println(wires["a"]!!.value.toUShort())
    }

    @Test
    fun part2() {
        val wires = getCircuit("b" to getCircuit()["a"]!!.value)
        println(wires["a"]!!.value.toUShort())
    }


}
