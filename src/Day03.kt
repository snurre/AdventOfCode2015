import org.junit.jupiter.api.Test
import java.io.File

class Day03 {
    data class House(val x: Int, val y: Int) {
        fun move(direction: String): House {
            return when (direction) {
                "^" -> House(x, y - 1)
                "v" -> House(x, y + 1)
                ">" -> House(x + 1, y)
                "<" -> House(x - 1, y)
                else -> this
            }
        }
    }

    private val directions: List<String> = File(this.javaClass.getResource("03.txt").path).readText().split("")

    @Test
    fun part1() {
        var house = House(0, 0)
        val houses = hashSetOf(house)
        directions.forEach { d ->
            house = house.move(d)
            houses.add(house)
        }
        println(houses.size)
    }

    @Test
    fun part2() {
        val santas = Array(2) { House(0, 0) }
        val houses = hashSetOf(santas[0])
        directions.withIndex().forEach { d ->
            val x = d.index % 2
            val house = santas[x].move(d.value)
            santas[x] = house
            houses.add(house)
        }
        println(houses.size)
    }
}
