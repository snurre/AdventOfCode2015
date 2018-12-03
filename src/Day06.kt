import org.junit.jupiter.api.Test
import java.io.File

class Day06 {
    data class Instruction(val a: String, val start: Point, val end: Point)
    data class Point(val x: Int, val y: Int)

    private val dimension = 1000
    private val pattern = Regex("(turn on|turn off|toggle) ([0-9]+),([0-9]+) through ([0-9]+),([0-9]+)")
    private val instructions: List<Instruction> =
        File(this.javaClass.getResource("06.txt").path).useLines {
            it.map { s ->
                val r = pattern.matchEntire(s)!!
                Instruction(
                    r.groupValues[1],
                    Point(r.groupValues[2].toInt(), r.groupValues[3].toInt()),
                    Point(r.groupValues[4].toInt(), r.groupValues[5].toInt())
                )
            }.toList()
        }

    private inline fun <reified T> getMatrix(startValue: T, operations: Map<String, (T) -> T>): Array<Array<T>> {
        val matrix = Array(dimension) { Array(dimension) { startValue } }
        instructions.forEach { i ->
            val op = operations[i.a]!!
            for (x in i.start.x..i.end.x) {
                for (y in i.start.y..i.end.y) {
                    matrix[x][y] = op.invoke(matrix[x][y])
                }
            }
        }
        return matrix
    }

    @Test
    fun part1() {
        val matrix = getMatrix(false, mapOf(
            "turn on" to { b -> true },
            "turn off" to { b -> false },
            "toggle" to { b -> !b }
        ))
        var lights = 0
        for (x in 0 until matrix.size) {
            for (y in 0 until matrix[x].size) {
                if (matrix[x][y]) {
                    lights++
                }
            }
        }
        println(lights)
    }

    @Test
    fun part2() {
        val matrix = getMatrix(0, mapOf(
            "turn on" to { i -> i + 1 },
            "turn off" to { i -> if (i > 0) i - 1 else 0 },
            "toggle" to { i -> i + 2 }
        ))
        var brightness = 0
        for (x in 0 until matrix.size) {
            for (y in 0 until matrix[x].size) {
                brightness += matrix[x][y]
            }
        }
        println(brightness)
    }
}
