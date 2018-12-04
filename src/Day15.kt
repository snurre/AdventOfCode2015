import org.junit.jupiter.api.Test
import java.io.File

class Day15 {
    data class Ingredient(val capacity: Int, val durability: Int, val flavor: Int, val texture: Int, val calories: Int)

    private val rx =
        Regex("(\\w+): capacity ([-0-9]+), durability ([-0-9]+), flavor ([-0-9]+), texture ([-0-9]+), calories ([-0-9]+)")
    private val ingredients: List<Ingredient> = File(this.javaClass.getResource("15.txt").path).useLines {
        it.map { s ->
            rx.matchEntire(s)!!.let { m ->
                Ingredient(
                    m.groupValues[2].toInt(),
                    m.groupValues[3].toInt(),
                    m.groupValues[4].toInt(),
                    m.groupValues[5].toInt(),
                    m.groupValues[6].toInt()
                )
            }
        }.toList()
    }
    private val combinations: List<List<Int>> by lazy {
        val combinations = arrayListOf<List<Int>>()
        permute(100, 0, combinations, ingredients.map { 0 })
        combinations
    }

    private fun permute(target: Int, index: Int, results: MutableList<List<Int>>, list: List<Int>) {
        for (i in 0..target) {
            val newList = list.toMutableList()
            newList[index] = i

            if (index < list.size - 1 && newList.subList(0, index).sum() <= target) {
                permute(target, index + 1, results, newList)
            }
            if (index == list.size - 1 && newList.sum() == target) {
                results.add(newList)
            }
        }
    }

    private fun sumAll(list: List<Ingredient>, amounts: List<Int>): Int {
        fun s(property: (Ingredient) -> Int): Int = Math.max(list.indices.sumBy { i -> property.invoke(list[i]) * amounts[i] }, 0)
        return s { i -> i.capacity } * s { i -> i.durability } * s { i -> i.flavor } * s { i -> i.texture }
    }

    @Test
    fun part1() {
        println(combinations.map { sumAll(ingredients, it) }.max())
    }

    @Test
    fun part2() {
        println(combinations.filter { ingredients.indices.sumBy { i -> ingredients[i].calories * it[i] } == 500 }
            .map { sumAll(ingredients, it) }
            .max())
    }
}
