package dev.slne.augmented.common.gui.gui

class Priority(val priority: Int) : Comparable<Priority> {

    /**
     * Compares this priority to another priority.
     * The comparison is based on the priority value.
     *
     * 0 if the priorities are equal
     *
     * -1 if this priority is lower than the other
     *
     * 1 if this priority is higher than the other
     *
     * @param other The other priority to compare to
     * @return the outcome
     */
    override fun compareTo(other: Priority): Int {
        return priority.compareTo(other.priority)
    }

    companion object {
        val LOWEST = Priority(-2)
        val LOW = Priority(-1)
        val NORMAL = Priority(0)
        val HIGH = Priority(1)
        val HIGHEST = Priority(2)
    }

}