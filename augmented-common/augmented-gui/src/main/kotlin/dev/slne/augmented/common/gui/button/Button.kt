package dev.slne.augmented.common.gui.button

import dev.slne.augmented.common.gui.event.GuiClickHandler
import dev.slne.augmented.common.gui.event.GuiDragHandler
import dev.slne.augmented.common.gui.gui.element.GuiElement
import dev.slne.augmented.common.gui.position.Positionable
import org.bukkit.inventory.ItemStack

interface Button<P : Positionable> : GuiElement {

    val icon: ItemStack

    var onDrag: GuiDragHandler?
    var onClick: GuiClickHandler?

    var position: P

}