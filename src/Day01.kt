fun main() {
    fun part1(input: List<String>): Int {
        var pointing = 50
        var zeroCount = 0

        fun adjustPointing(direction: String, count: Int) {
            when (direction) {
                "L" -> pointing -= count
                "R" -> pointing += count
            }

            pointing = Math.floorMod(pointing, 100)
        }

        fun adjustZeroCount() {
            if (pointing == 0) {
                zeroCount++
            }
        }

        input.forEach { line ->
            val direction = line.take(1)
            val count = line.substring(1).toInt()

            adjustPointing(direction, count)
            adjustZeroCount()
        }

        return zeroCount
    }

//    fun part2(input: List<String>): Int {
//        println("My part2")
//        var pointing = 50
//        var zeroCount = 0
//
//        fun getOutboundCount(pointing: Int): Int {
//            val n = abs(pointing) + if (pointing < 0) 100 else 0
//            return ceil(n / 100.0).toInt() - 1
//        }
//
//        fun adjustPointing(direction: String, count: Int) {
//            val previousPointing = pointing
//
//            when (direction) {
//                "L" -> {
//                    pointing -= count
//                }
//                "R" -> {
//                    pointing += count
//                }
//            }
//
//            if (previousPointing != 0 && pointing !in 0..100) {
//                zeroCount += getOutboundCount(pointing)
//            }
//
//            pointing = Math.floorMod(pointing, 100)
//        }
//
//        fun adjustZeroCount() {
//            if (pointing == 0) {
//                zeroCount++
//            }
//        }
//
//        input.forEach { line ->
//            val direction = line.take(1)
//            val count = line.substring(1).toInt()
//
//            adjustPointing(direction, count)
//            adjustZeroCount()
//
//            println("$line: $zeroCount")
//        }
//
//        return zeroCount
//    }

    fun part2(input: List<String>): Int {
        var position = 50
        var zeroCount = 0

        for (line in input) {
            if (line.isEmpty()) continue
            val direction = line[0]
            val distance = line.substring(1).toInt()

            repeat(distance) {
                position = when (direction) {
                    'L' -> if (position == 0) 99 else position - 1
                    'R' -> if (position == 99) 0 else position + 1
                    else -> position
                }
                if (position == 0) {
                    zeroCount++
                }
            }
            println("$line: $zeroCount")
        }

        return zeroCount
    }

//     Test if implementation meets criteria from the description, like:
//    check(part1(listOf("test_input")) == 1)

//     Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 3)
    check(part2(testInput) == 6)

//     Read the input from the `src/Day01.txt` file.
    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}
