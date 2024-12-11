package dev.slne.augmented.common.gui.position.area

import dev.slne.augmented.common.gui.position.Position
import dev.slne.augmented.common.gui.position.area.areas.CuboidArea

object Areas {
    fun forSize(size: Int) = CuboidArea(Position(), Position(size - 1))

    fun horizontalLine(startX: Int, endX: Int, y: Int) =
        CuboidArea(Position(startX, y), Position(endX, y))

    fun horizontalLineIn(area: CuboidArea, y: Int) =
        CuboidArea(Position(area.upperLeft.x, y), Position(area.bottomRight.x, y))

    fun fullHorizontalLine(y: Int) = horizontalLine(0, 8, y)

    fun verticalLine(startY: Int, endY: Int, x: Int) =
        CuboidArea(Position(x, startY), Position(x, endY))

    fun verticalLineIn(area: CuboidArea, x: Int) =
        CuboidArea(Position(x, area.upperLeft.y), Position(x, area.bottomRight.y))

    fun fullVerticalLine(x: Int, height: Int) = verticalLine(0, height, x)

    val AREA_ANVIL = forSize(3)
    val AREA_1X9 = forSize(9)
    val AREA_2X9 = forSize(18)
    val AREA_3X9 = forSize(27)
    val AREA_4X9 = forSize(36)
    val AREA_5X9 = forSize(45)
    val AREA_6X9 = forSize(54)
}