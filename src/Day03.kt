fun main() {
  fun List<String>.toLongs() = map { line ->
    line.map { it.digitToInt().toLong() }
  }

  fun List<Long>.getDigitIndexes(digitToFind: Long) =
    mapIndexedNotNull { index, value ->
      if (value == digitToFind) index else null
    }

  fun List<Long>.getHighestDigitIndexes() =
    getDigitIndexes(max())

  fun List<Long>.getHighestDigitIndex() =
    max().let { max ->
      indexOfFirst { it == max }
    }

  fun List<Long>.getHighestDigits(numberOfDigitsToFind: Int): List<Long> {
    dropLast(numberOfDigitsToFind - 1).let { list ->
      val digitIndex = list.getHighestDigitIndex()
      val digit = get(digitIndex)
      return if (numberOfDigitsToFind > 1) {
        listOf(digit) + subList(digitIndex + 1, size).getHighestDigits(numberOfDigitsToFind - 1)
      } else {
        listOf(digit)
      }
    }
  }

  fun List<Long>.getMaxJoltage(numberOfDigitsToFind: Int): Long {
    val highestIndexes = dropLast(numberOfDigitsToFind - 1).getHighestDigitIndexes()
    var highestJoltage = 0L

    highestIndexes.forEach { index ->
      val highestDigits = listOf(get(index)) + subList(index + 1, size).getHighestDigits(numberOfDigitsToFind - 1)
      val joltage = highestDigits.joinToString("").toLong()
      if (joltage > highestJoltage) {
        highestJoltage = joltage
      }
    }

    return highestJoltage
  }

  fun List<List<Long>>.getMaxJoltage(digitsToFind: Int) =
    sumOf { bank ->
      bank.getMaxJoltage(digitsToFind)
    }

  fun part1(input: List<String>) = input.toLongs().getMaxJoltage(2)

  fun part2(input: List<String>) = input.toLongs().getMaxJoltage(12)

//     Test if implementation meets criteria from the description, like:
//    check(part1(listOf("test_input")) == 1)

//     Or read a large test input from the `src/Day03_test.txt` file:
  val testInput = readInput("Day03_test")
  check(part1(testInput) == 357L)
  check(part2(testInput) == 3121910778619L)

//     Read the input from the `src/Day03.txt` file.
  val input = readInput("Day03")
  part1(input).println()
  part2(input).println()
}
