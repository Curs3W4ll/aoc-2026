typealias Range = Pair<Long, Long>

fun main() {
  fun Range.toKRange() = first..second

  fun Range.contains(n: Long) = toKRange().contains(n)

  fun List<String>.toLongs() = map { it.toLong() }

  fun <T> List<T>.toPair() = get(0) to get(1)

  fun <T> List<T>.dropIndex(index: Int) =
    take(index) + takeLast(size - index - 1)

  fun <T> List<T>.set(index: Int, value: T) =
    take(index) + value + takeLast(size - index - 1)

  fun List<String>.toLongPairs() = map { line ->
    line.split("-").map { it.toLong() }.toPair()
  }

  fun List<String>.process(): Pair<List<Range>, List<Long>> {
    val blankIndex = indexOfFirst { it.isEmpty() }

    return take(blankIndex).toLongPairs() to takeLast(size - blankIndex - 1).toLongs()
  }

  fun Long.isFresh(freshRanges: List<Range>) =
    freshRanges.any {
      this in it.toKRange()
    }

  fun List<Range>.removeDuplicates() =
    toSet().toList()

  fun List<Range>.removeContainedRanges() =
    filterNot { range ->
      any { otherRange ->
        range != otherRange && range.first in otherRange.toKRange() && range.second in otherRange.toKRange()
      }
    }

  fun List<Range>.mergeContinuousRanges(): List<Range> {
    forEachIndexed { index, range ->
      forEachIndexed { otherIndex, otherRange ->
        if (index != otherIndex) {
          if (otherRange.contains(range.first)) {
            return set(otherIndex, otherRange.first to range.second).dropIndex(index).mergeContinuousRanges()
          }
          if (otherRange.contains(range.second)) {
            return set(otherIndex, range.first to otherRange.second).dropIndex(index).mergeContinuousRanges()
          }
        }
      }
    }

    return this
  }

  fun part1(input: List<String>): Int {
    val (ranges, ingredients) = input.process()

    return ingredients.count { it.isFresh(ranges) }
  }

  fun List<Range>.ingredientNumber() = sumOf { it.second - it.first + 1 }

  fun part2(input: List<String>): Long {
    val (ranges, _) = input.process()

    return ranges.removeDuplicates().removeContainedRanges().mergeContinuousRanges().ingredientNumber()
  }

//     Test if implementation meets criteria from the description, like:
//    check(part1(listOf("test_input")) == 1)

//     Or read a large test input from the `src/Day05_test.txt` file:
  val testInput = readInput("Day05_test")
  check(part1(testInput) == 3)
  check(part2(testInput) == 14L)

//     Read the input from the `src/Day05.txt` file.
  val input = readInput("Day05")
  part1(input).println()
  part2(input).println()
}
