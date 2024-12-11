package dev.slne.augmented.common.gui.position

import com.google.common.base.Preconditions
import org.bukkit.inventory.Inventory

class Position() : Positionable, Cloneable {

    var x: Int = 0
        set(value) {
            Preconditions.checkArgument(value >= 0, "X must be greater or equal to 0")
            field = value
        }

    var y: Int = 0
        set(value) {
            Preconditions.checkArgument(value >= 0, "Y must be greater or equal to 0")
            field = value
        }

    var gridWidth: Int = 9
        set(value) {
            Preconditions.checkArgument(value >= 0, "Grid width must be greater or equal to 0")
            field = value
        }

    constructor(x: Int, y: Int, gridWidth: Int = 9) : this() {
        this.x = x
        this.y = y
        this.gridWidth = gridWidth
    }

    constructor(absolute: Int) : this() {
        fromAbsolute(absolute)
    }

    fun fromAbsolute(absolute: Int) {
        Preconditions.checkArgument(absolute >= 0, "Absolute must be greater or equal to 0")

        var x = absolute
        var y = 0

        while (x >= gridWidth) {
            x -= gridWidth
            y++
        }

        this.y = y
        this.x = x

        if (toAbsolute() != absolute) {
            throw RuntimeException("absolute number not same!")
        }
    }

    fun toAbsolute() = y * gridWidth + x

    fun normalize(block: () -> Unit): Position {
        block()

        if (x >= gridWidth || x < 0) {
            y += x / gridWidth
            x %= gridWidth
        }

        return this
    }

    fun down(amount: Int) = this.normalize {
        y += amount
    }

    fun up(amount: Int) = this.normalize {
        y -= amount
    }

    fun left(amount: Int) = this.normalize {
        x -= amount
    }

    fun right(amount: Int) = this.normalize {
        x += amount
    }

    fun down() = down(1)
    fun up() = up(1)
    fun left() = left(1)
    fun right() = right(1)

    fun isValid() = x >= 0 && y >= 0

    fun fits(inventory: Inventory) = fits(inventory.size)
    fun fits(size: Int): Boolean = toAbsolute() in 0..<size

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Position

        if (x != other.x) return false
        if (y != other.y) return false

        return true
    }

    override fun hashCode(): Int {
        var result = x
        result = 31 * result + y
        return result
    }

    public override fun clone() = Position(x, y, gridWidth)

    override fun toString(): String {
        return "Position(x=$x, y=$y, absolute=${toAbsolute()})"
    }

    operator fun plus(other: Position): Position {
        return Position(x + other.x, y + other.y)
    }

    operator fun minus(other: Position): Position {
        return Position(x - other.x, y - other.y)
    }

    operator fun compareTo(other: Position): Int {
        return toAbsolute().compareTo(other.toAbsolute())
    }

    operator fun rangeTo(other: Position): List<Position> {
        val positions = mutableListOf<Position>()
        val otherIsSmaller = other < this

        var currentPosition = this
        while (currentPosition != other) {
            positions.add(currentPosition)

            currentPosition =
                if (otherIsSmaller) currentPosition.left() else currentPosition.right()
        }

        println("Valid Positions: $positions")

        return positions
    }

    companion object {
        fun of(x: Int, y: Int): Position {
            return Position(x, y)
        }
    }
}