package dev.slne.augmented.common.gui.gui

@JvmInline
value class Priority(val priority: Int) : Comparable<Priority> {

    override fun compareTo(other: Priority): Int {
        return priority.compareTo(other.priority)
    }

    companion object {
        val LOWEST = Priority(-20)
        val LOW = Priority(-10)
        val NORMAL = Priority(0)
        val HIGH = Priority(10)
        val HIGHEST = Priority(20)
    }
}