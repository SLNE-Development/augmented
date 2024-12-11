package dev.slne.augmented.common.gui.position.area.areas

import dev.slne.augmented.common.gui.position.Position
import dev.slne.augmented.common.gui.position.area.Area

class CuboidArea() : Area(), Iterable<Position> {

    var upperLeft: Position = Position()
    var bottomRight: Position = Position()

    constructor(upperLeft: Position, bottomRight: Position) : this() {
        this.upperLeft = upperLeft
        this.bottomRight = bottomRight
    }

    override fun containsPosition(position: Position): Boolean {
        val positionX = position.x
        val positionY = position.y

        return positionX >= upperLeft.x && positionX <= bottomRight.x && positionY >= upperLeft.y && positionY <= bottomRight.y
    }

    override fun getSize() = (bottomRight.x - upperLeft.x + 1) * (bottomRight.y - upperLeft.y + 1)

    override fun isContainedInOtherArea(area: Area): Boolean {
        if (area is CuboidArea) {
            return area.upperLeft.x >= upperLeft.x && area.upperLeft.y >= upperLeft.y && area.bottomRight.x <= bottomRight.x && area.bottomRight.y <= bottomRight.y
        }

        return false
    }

    override fun iterator(): Iterator<Position> {
        return object : Iterator<Position> {
            val position = upperLeft

            override fun hasNext() = this@CuboidArea.containsPosition(position)

            override fun next(): Position {
                if (!hasNext()) {
                    throw NoSuchElementException()
                }

                val next = position.clone()
                next.right()

                if (bottomRight.x < next.x) {
                    next.x = upperLeft.x
                    next.down()
                }

                return next
            }
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as CuboidArea

        if (upperLeft != other.upperLeft) return false
        if (bottomRight != other.bottomRight) return false

        return true
    }

    override fun hashCode(): Int {
        var result = upperLeft.hashCode()
        result = 31 * result + bottomRight.hashCode()
        return result
    }

    override fun toString(): String {
        return "CuboidArea(upperLeft=$upperLeft, bottomRight=$bottomRight)"
    }


}