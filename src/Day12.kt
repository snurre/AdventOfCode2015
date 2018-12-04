import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.node.ArrayNode
import com.fasterxml.jackson.databind.node.IntNode
import com.fasterxml.jackson.databind.node.ObjectNode
import org.junit.jupiter.api.Test
import java.io.File

class Day12 {
    private val data = File(this.javaClass.getResource("12.txt").path).readText()

    private fun x(obj: JsonNode): Int = when (obj) {
        is IntNode -> obj.intValue()
        is ArrayNode -> obj.sumBy { x(it) }
        is ObjectNode -> obj.fields().asSequence().sumBy { f -> if (f.key == "red" || f.value.textValue() == "red") return 0 else x(f.value) }
        else -> 0
    }

    @Test
    fun part1() {
        println(Regex("(-?[0-9]+)").findAll(data).sumBy { it.groupValues[1].toInt() })
    }

    @Test
    fun part2() {
        println(x(ObjectMapper().readTree(data)))
    }
}
