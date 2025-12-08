import kotlin.math.sqrt

fun main() {
  fun List<String>.toLongs() = map { it.toLong() }

  fun List<String>.toCircuits(): List<List<Long>> = map {
    it.split(",").toLongs()
  }

  fun List<Long>.getDistance(other: List<Long>): Double {
    val dx = (get(0) - other[0]).toDouble()
    val dy = (get(1) - other[1]).toDouble()
    val dz = (get(2) - other[2]).toDouble()

    return sqrt(dx * dx + dy * dy + dz * dz)
  }

  fun List<List<Long>>.getDistances(): List<Pair<Pair<List<Long>, List<Long>>, Double>> {
    val distances = mutableListOf<Pair<Pair<List<Long>, List<Long>>, Double>>()

    for (x in 0..<size) {
      for (y in (x + 1)..<size) {
        distances.add((get(x) to get(y)) to get(x).getDistance(get(y)))
      }
    }

    return distances
  }

  fun MutableList<List<List<Long>>>.merge(index1: Int, index2: Int) {
    val lowIndex = minOf(index1, index2)
    val highIndex = maxOf(index1, index2)

    set(lowIndex, get(lowIndex) + removeAt(highIndex))
  }

  fun MutableList<List<List<Long>>>.add(index: Int, element: List<Long>) {
    set(index, get(index) + listOf(element))
  }

  fun MutableList<List<List<Long>>>.add(list1: List<Long>, list2: List<Long>) {
    val existingGroup1Index = indexOfFirst { it.contains(list1) }
    val existingGroup2Index = indexOfFirst { it.contains(list2) }

    if (existingGroup1Index != -1 && existingGroup2Index != -1) {
      if (existingGroup1Index == existingGroup2Index) return
      merge(existingGroup1Index, existingGroup2Index)
    } else if (existingGroup1Index != -1) {
      add(existingGroup1Index, list2)
    } else if (existingGroup2Index != -1) {
      add(existingGroup2Index, list1)
    } else {
      add(listOf(list1, list2))
    }
  }

  fun part1(input: List<String>, connectionsNumber: Int): Int {
    val circuits = input.toCircuits()
    val groups = mutableListOf<List<List<Long>>>()
    val distances = circuits.getDistances().sortedBy { it.second }.take(connectionsNumber)

    distances.forEach { (lists, _) ->
      groups.add(lists.first, lists.second)
    }

    return groups.map { it.size }.sorted().takeLast(3).reduce { acc, i -> acc * i }
  }

  fun part2(input: List<String>): Long {
    val circuits = input.toCircuits()
    val groups = mutableListOf<List<List<Long>>>()
    val distances = circuits.getDistances().sortedBy { it.second }

    for (i in 0..<distances.size) {
      val lists = distances[i].first
      groups.add(lists.first, lists.second)

      if (groups.size == 1 && groups.first().size == circuits.size) {
        return lists.first.first() * lists.second.first()
      }
    }

    return 0
  }

//     Test if implementation meets criteria from the description, like:
//    check(part1(listOf("test_input")) == 1)

//     Or read a large test input from the `src/Day08_test.txt` file:
  val testInput = readInput("Day08_test")
  check(part1(testInput, 10) == 40)
  check(part2(testInput) == 25272L)

//     Read the input from the `src/Day08.txt` file.
  val input = readInput("Day08")
  part1(input, 1000).println()
  part2(input).println()
}
