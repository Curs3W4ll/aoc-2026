typealias Vector = List<Int>

data class Machine(
  val lightIndicator: Vector,
  val buttons: List<Vector>,
  val joltage: Vector,
)

fun main() {
  fun String.toVectorFromHashtags(): Vector = map {
    when (it) {
      '.' -> 0
      '#' -> 1
      else -> throw IllegalArgumentException("Invalid character: $it")
    }
  }

  fun List<Int>.toVectorFromInts(size: Int): Vector {
    val list = MutableList(size) { 0 }
    forEach { pos ->
      list[pos] = 1
    }

    return list
  }

  fun String.toInts() = split(",").map { it.toInt() }

  fun String.trim(start: Char, end: Char) = trimStart(start).trimEnd(end)

  fun String.parse() =
    split(" ")
      .let {
        val goal = it.first().trim('[', ']').toVectorFromHashtags()
        Machine(
          lightIndicator = goal,
          buttons = it.drop(1).dropLast(1).map { b -> b.trim('(', ')').toInts().toVectorFromInts(goal.size) },
          joltage = it.last().trim('{', '}').toInts(),
        )
      }

  fun List<String>.parse() = map { it.parse() }

  operator fun Vector.plus(other: Vector) = zip(other) { a, b -> a + b }

  fun List<Vector>.sumWithModulo() = reduce { acc, v -> acc + v }.map { it % 2 }

  fun List<Vector>.sum() = reduce { acc, v -> acc + v }

  fun <T> List<T>.combinations(n: Int): List<List<T>> {
    if (n == 0) return listOf(emptyList())
    if (size < n) return emptyList()

    if (n == 1) return map { listOf(it) }

    val result = mutableListOf<List<T>>()

    for (i in indices) {
      val elem = get(i)
      val rest = drop(i + 1)
      for (combo in rest.combinations(n - 1)) {
        result += listOf(elem) + combo
      }
    }

    return result
  }

  fun <T> List<T>.combinationsWithRepetition(n: Int): List<List<T>> {
    fun helper(start: Int, k: Int): List<List<T>> {
      if (k == 0) return listOf(emptyList())
      val result = mutableListOf<List<T>>()

      for (i in start until size) {
        for (tail in helper(i, k - 1)) {
          result += listOf(get(i)) + tail
        }
      }

      return result
    }
    return helper(0, n)
  }

  fun Machine.getLowestPressesForLightIndicator(): Int {
    for (i in 1..1000) {
      val combinations = buttons.combinations(i)
      combinations.forEach { combination ->
        if (combination.sumWithModulo() == lightIndicator) return i
      }
    }
    throw Exception("Reach maximum try without finding solution")
  }

  fun Machine.getLowestPressesForJoltage(): Int {
    for (i in 1..1000) {
      val combinations = buttons.combinationsWithRepetition(i)
      combinations.forEach { combination ->
        if (combination.sum() == joltage) return i
      }
    }
    throw Exception("Reach maximum try without finding solution")
  }

  fun part1(input: List<String>): Int {
    val machines = input.parse()

    return machines.sumOf { machine ->
      machine.getLowestPressesForLightIndicator()
    }
  }

  fun part2(input: List<String>): Int {
    val machines = input.parse()

    return machines.sumOf { machine ->
      machine.getLowestPressesForJoltage()
    }
  }

//     Test if implementation meets criteria from the description, like:
//    check(part1(listOf("test_input")) == 1)

//     Or read a large test input from the `src/Day10_test.txt` file:
  val testInput = readInput("Day10_test")
  check(part1(testInput) == 7)
  check(part2(testInput) == 33)

//     Read the input from the `src/Day10.txt` file.
  val input = readInput("Day10")
  part1(input).println()
  part2(input).println()
}
