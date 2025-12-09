import kotlin.math.abs

typealias Coord = Pair<Int, Int>

fun main() {
  fun List<String>.toInts() = map { it.toInt() }

  fun List<String>.toCoords(): List<Coord> = map { coord ->
    coord.split(",").toInts().let { it.first() to it[1] }
  }

  fun Coord.squareSizeWith(other: Coord) =
    (abs(first - other.first) + 1).toLong() * (abs(second - other.second) + 1).toLong()

  fun List<Coord>.getSquareSizes(): List<Pair<Pair<Coord, Coord>, Long>> {
    val distances = mutableListOf<Pair<Pair<Coord, Coord>, Long>>()

    for (x in 0..<size) {
      for (y in (x + 1)..<size) {
        distances.add((get(x) to get(y)) to get(x).squareSizeWith(get(y)))
      }
    }

    return distances
  }

  fun Pair<Coord, Coord>.getInvertCoords() =
    (first.first to second.second) to (second.first to first.second)

  fun Coord.isInsideFill(coords: List<Coord>): Boolean {
    val haveTopLeft = coords.any { it.first <= first && it.second <= second }
    val haveTopRight = coords.any { it.first >= first && it.second <= second }
    val haveBottomLeft = coords.any { it.first <= first && it.second >= second }
    val haveBottomRight = coords.any { it.first >= first && it.second >= second }

    return haveTopLeft && haveTopRight && haveBottomLeft && haveBottomRight
  }

  fun part1(input: List<String>): Long {
    val coords = input.toCoords()
    val squareSizes = coords.getSquareSizes()

    return squareSizes.maxOf { it.second }
  }

  fun part2(input: List<String>): Long {
    val allCoords = input.toCoords()
    val squareSizes = allCoords.getSquareSizes()

    return squareSizes
      .filter { (coords, _) ->
        val invertCoords = coords.getInvertCoords()

        invertCoords.first.isInsideFill(allCoords) && invertCoords.second.isInsideFill(allCoords)
      }
      .maxOf { it.second }
  }

//     Test if implementation meets criteria from the description, like:
//    check(part1(listOf("test_input")) == 1)

//     Or read a large test input from the `src/Day09_test.txt` file:
  val testInput = readInput("Day09_test")
  check(part1(testInput) == 50L)
  check(part2(testInput) == 24L)

//     Read the input from the `src/Day09.txt` file.
  val input = readInput("Day09")
  part1(input).println()
  part2(input).println()
}
