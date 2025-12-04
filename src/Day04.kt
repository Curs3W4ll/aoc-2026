fun main() {
  fun Char.isPaper() = this == '@'

  fun List<String>.isMovable(row: Int, col: Int): Boolean {
    var adjacentPapers = 0

    for (rowOffset in -1..1) {
      for (colOffset in -1..1) {
        if (rowOffset == 0 && colOffset == 0) continue

        val adjacentChar = getOrNull(row + rowOffset)?.getOrNull(col + colOffset)
        if (adjacentChar != null && adjacentChar.isPaper()) {
          adjacentPapers++
        }
      }
    }

    return adjacentPapers < 4
  }

  fun part1(input: List<String>): Int {
    var count = 0

    input.forEachIndexed { row, line ->
      line.forEachIndexed { col, char ->
        if (char.isPaper() && input.isMovable(row, col)) {
          count++
        }
      }
    }

    return count
  }

  fun MutableList<String>.countMovablePapers(): Int {
    var count = 0

    forEachIndexed { row, line ->
      line.forEachIndexed { col, char ->
        if (char.isPaper() && isMovable(row, col)) {
          count++
          this[row] = this[row].substring(0, col) + '.' + this[row].substring(col + 1)
        }
      }
    }

    return count
  }

  fun part2(i: List<String>): Int {
    val input = i.toMutableList()
    var totalCount = 0

    do {
      val count = input.countMovablePapers()
      totalCount += count
    } while (count > 0)

    return totalCount
  }

//     Test if implementation meets criteria from the description, like:
//    check(part1(listOf("test_input")) == 1)

//     Or read a large test input from the `src/Day04_test.txt` file:
  val testInput = readInput("Day04_test")
  check(part1(testInput) == 13)
  check(part2(testInput) == 43)

//     Read the input from the `src/Day04.txt` file.
  val input = readInput("Day04")
  part1(input).println()
  part2(input).println()
}
