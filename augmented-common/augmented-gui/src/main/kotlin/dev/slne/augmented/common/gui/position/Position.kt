package dev.slne.augmented.common.gui.position

import com.google.common.base.Preconditions
import dev.slne.augmented.common.gui.pane.GuiPane

class Position(val x: Int, val y: Int) {

    init {
        Preconditions.checkArgument(x >= 0, "X must be greater or equal to 0")
        Preconditions.checkArgument(y >= 0, "Y must be greater or equal to 0")
    }

    fun inBounds(guiPane: GuiPane): Boolean {
        return x in 0 until guiPane.width && y in 0 until guiPane.height
    }

    fun toAbsolute(): Int {
        return y * 9 + x
    }

    fun next(): Position {
        return fromAbsolute(toAbsolute() + 1)
    }

    fun previous(): Position {
        return fromAbsolute(toAbsolute() - 1)
    }

    companion object {
        fun from(x: Int, y: Int): Position {
            return Position(x, y)
        }

        fun fromAbsolute(absolute: Int): Position {
            return Position(absolute % 9, absolute / 9)
        }
    }
}