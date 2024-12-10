package dev.slne.augmented.common.gui.button

import dev.slne.augmented.common.gui.gui.Priority
import dev.slne.augmented.common.gui.gui.element.AbstractGuiElement
import dev.slne.augmented.common.gui.position.Position
import org.bukkit.inventory.ItemStack

class GuiButton(
    val itemStack: ItemStack,
    override val position: Position,
    override val priority: Priority = Priority.NORMAL,
    override val width: Int = 1,
    override val height: Int = 1
) : AbstractGuiElement(position = position, priority = priority, width = width, height = height)