package dev.slne.augmented.common.gui.pane

import dev.slne.augmented.common.gui.gui.Priority
import dev.slne.augmented.common.gui.gui.element.AbstractGuiElement
import dev.slne.augmented.common.gui.position.Position

class GuiPane(
    override val width: Int,
    override val height: Int,
    override val position: Position,
    override val priority: Priority = Priority.NORMAL
) : AbstractGuiElement(position = position, priority = priority, width = width, height = height)