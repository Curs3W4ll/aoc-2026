fun main() {
  fun List<String>.simulateForLine(lineIndex: Int, beamIndex: Int): Int {
    if (lineIndex == size - 1) return 0

    return when (get(lineIndex)[beamIndex]) {
      '^' -> {
        simulateForLine(lineIndex + 1, beamIndex - 1) + simulateForLine(lineIndex + 1, beamIndex + 1) + 1
      }

      else -> {
        simulateForLine(lineIndex + 1, beamIndex)
      }
    }
  }

  fun part1(input: List<String>): Int {
    var splitNumber = 0
    var lineIndex = 0
    val beamIndexes = mutableSetOf(input.first().indexOf("S"))
    val nextIndexes = mutableSetOf<Int>()

    while (lineIndex < input.size) {
      beamIndexes.forEach { index ->
        when (input[lineIndex][index]) {
          '^' -> {
            splitNumber++
            nextIndexes.add(index - 1)
            nextIndexes.add(index + 1)
          }

          else -> {
            nextIndexes.add(index)
          }
        }
      }
      beamIndexes.clear()
      beamIndexes.addAll(nextIndexes)
      nextIndexes.clear()
      lineIndex++
    }

    return splitNumber
  }

  fun MutableMap<Int, Long>.registerAt(i: Int, times: Long) {
    this[i] = (this[i] ?: 0) + times
  }

  fun part2(input: List<String>): Long {
    var lineIndex = 0
    val beamIndexes = mutableMapOf(input.first().indexOf("S") to 1L)
    val nextIndexes = mutableMapOf<Int, Long>()

    while (lineIndex < input.size) {
      beamIndexes.forEach { (index, times) ->
        when (input[lineIndex][index]) {
          '^' -> {
            nextIndexes.registerAt(index - 1, times)
            nextIndexes.registerAt(index + 1, times)
          }

          else -> {
            nextIndexes.registerAt(index, times)
          }
        }
      }
      beamIndexes.clear()
      beamIndexes.putAll(nextIndexes)
      nextIndexes.clear()
      lineIndex++
    }

    return beamIndexes.values.sum()
  }

//     Test if implementation meets criteria from the description, like:
//    check(part1(listOf("test_input")) == 1)

//     Or read a large test input from the `src/Day07_test.txt` file:
  val testInput = readInput("Day07_test")
  check(part1(testInput) == 21)
  check(part2(testInput) == 40L)

//     Read the input from the `src/Day07.txt` file.
  val input = readInput("Day07")
  part1(input).println()
  part2(input).println()
}
