package skywolf46.refnbt.util

class BukkitVersion(val major: Int, val minor: Int, val revision: Int) : Comparable<BukkitVersion> {
    override fun compareTo(other: BukkitVersion): Int {
        return major.compareTo(other.major).let { majorCheck ->
            if (majorCheck == 0) {
                return@let minor.compareTo(other.minor).let { minorCheck ->
                    if (minorCheck == 0) {
                        return revision.compareTo(other.revision)
                    }
                    return@let minorCheck
                }
            }
            return@let majorCheck
        }
    }


    fun isLegacy(ver: BukkitVersion): Boolean {
        return compareTo(ver) == 1
    }


    fun isFuture(ver: BukkitVersion): Boolean {
        return compareTo(ver) == -11
    }


    fun isCurrent(ver: BukkitVersion): Boolean {
        return compareTo(ver) == 0
    }

    override fun equals(other: Any?): Boolean {
        return (other is BukkitVersion && isCurrent(other))
    }
}

fun main() {
    println(BukkitVersion(1, 12, 2) < BukkitVersion(1, 7, 10))
}