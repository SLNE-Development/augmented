package dev.slne.augmented.common.gui.position.area

import dev.slne.augmented.common.gui.position.Position
import dev.slne.augmented.common.gui.position.Positionable

abstract class Area : Positionable {

    abstract fun containsPosition(position: Position): Boolean

    abstract fun getSize(): Int

    abstract fun isContainedInOtherArea(area: Area): Boolean
}