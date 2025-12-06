fun main() {
  fun <T> List<List<T>>.transpose(): List<List<T>> {
    require(this.isNotEmpty()) { "Cannot transpose an empty matrix" }
    val cols = this[0].size
    return (0 until cols).map { c ->
      this.map { row -> row[c] }
    }
  }

  fun List<String>.normalize() = map { it.replace("\\s+".toRegex(), " ").trim().split(" ") }

  fun List<List<String>>.toLongs() = map { line -> line.map { it.toLong() } }

  fun Long.applyOperation(operation: String, n: Long) = when (operation) {
    "+" -> this + n
    "-" -> this - n
    "*" -> this * n
    "/" -> this / n
    else -> throw IllegalArgumentException("Invalid operation: $operation")
  }

  fun List<Long>.applyOperation(operation: String) =
    reduce { acc, n -> acc.applyOperation(operation, n) }

  fun List<String>.equalizeLength(): List<String> {
    val maxLength = maxOf { it.length }

    return this.map { it.padEnd(maxLength, ' ') }
  }

//  fun List<String>.cephalopod(): List<Long> {
//    val maxLength = this.maxOf { it.length }
//
//    return (0 until maxLength).map { index ->
//      this.mapNotNull { it.getOrNull(it.length - 1 - index) }.joinToString("").toLong()
//    }
//  }

  fun part1(input: List<String>): Long {
    val normalized = input.normalize()
    val numbers = normalized.dropLast(1).toLongs().transpose()
    val operations = normalized.last()

    return numbers.mapIndexed { index, line ->
      line.applyOperation(operations[index])
    }.sum()
  }

  fun part2(input: List<String>): Long {
    val numbers = input
      .dropLast(1)
      .equalizeLength()
      .map { it.split("").drop(1).dropLast(1) }
      .transpose()
      .map { it.joinToString("").trim() }
      .joinToString(" ")
      .split("  ")
      .map { it.split(" ") }
      .toLongs()
    val operations = input.normalize().last()

    return numbers.mapIndexed { index, line ->
      line.applyOperation(operations[index])
    }.sum()
  }

//     Test if implementation meets criteria from the description, like:
//    check(part1(listOf("test_input")) == 1)

//     Or read a large test input from the `src/Day06_test.txt` file:
  val testInput = readInput("Day06_test")
  check(part1(testInput) == 4277556L)
  check(part2(testInput) == 3263827L)

//     Read the input from the `src/Day06.txt` file.
  val input = readInput("Day06")
  part1(input).println()
  part2(input).println()
}
