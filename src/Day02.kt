import kotlin.math.pow

fun main() {
    fun Long.getNumberOfDigits() = toString().removePrefix("-").length

    fun Long.haveEvenDigits() = getNumberOfDigits() % 2 == 0

    fun Long.getNextPowerOfTen(): Long {
        val targetDigitsNumber = (this * 10).getNumberOfDigits()
        return 10.0.pow(targetDigitsNumber - 1).toLong()
    }

    fun Long.double() = toString().repeat(2).toLong()

    fun Long.getHalf(): Long {
        val s = toString()
        return s.take(s.length / 2).toLong()
    }

    fun Long.take(n: Int) = toString().take(n).toLong()

    fun Long.addDigits(n: Long) = 10.0.pow(n.getNumberOfDigits()).toLong() * this + n

    fun part1(input: List<String>): Long {
        fun Long.isInvalid(): Boolean {
            val s = toString()
            val mid = s.length / 2
            val first = s.take(mid)
            val second = s.takeLast(mid)

            return first == second
        }

        fun Long.getNextMirrorNumber(): Long {
            val half = getHalf()

            return (half + 1).double()
        }

        fun Long.getStartingId(): Long {
            if (haveEvenDigits()) {
                val mirror = this.getHalf().double()
                if (mirror < this) {
                    mirror.getNextMirrorNumber()
                } else {
                    return mirror
                }
                return this
            }
            return getNextPowerOfTen().getStartingId()
        }

        val ids = input.flatMap { it.split(",") }
        val invalidIds = mutableListOf<Long>()

        ids.forEach { id ->
            val (start, end) = id.split("-", limit = 2).map { it.toLong() }

            var currentId = start.getStartingId()
            while (currentId <= end) {
                if (currentId.isInvalid()) {
                    invalidIds.add(currentId)
                }
                currentId = currentId.getNextMirrorNumber()
            }
        }

        return invalidIds.sum()
    }

    fun part2(input: List<String>): Long {
        fun Long.isInvalid(): Boolean {
            for (i in 1..(getNumberOfDigits() / 2)) {
                val patternToRepeat = take(i)
                var current = patternToRepeat

                while (current <= this) {
                    if (current == this) {
                        return true
                    }
                    current = current.addDigits(patternToRepeat)
                }
            }

            return false
        }

        val ids = input.flatMap { it.split(",") }
        val invalidIds = mutableListOf<Long>()

        ids.forEach { id ->
            val (start, end) = id.split("-", limit = 2).map { it.toLong() }

            for (n in start..end) {
                if (n.isInvalid()) {
                    invalidIds.add(n)
                }
            }
        }

        return invalidIds.sum()
    }

//     Test if implementation meets criteria from the description, like:
//    check(part1(listOf("test_input")) == 1)

//     Or read a large test input from the `src/Day02_test.txt` file:
    val testInput = readInput("Day02_test")
    check(part1(testInput) == 1227775554L)
    check(part2(testInput) == 4174379265L)

//     Read the input from the `src/Day02.txt` file.
    val input = readInput("Day02")
    part1(input).println()
    part2(input).println()
}
