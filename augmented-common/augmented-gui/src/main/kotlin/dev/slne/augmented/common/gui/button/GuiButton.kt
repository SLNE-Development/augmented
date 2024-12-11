package dev.slne.augmented.common.gui.button

import dev.slne.augmented.common.gui.event.GuiClickHandler
import dev.slne.augmented.common.gui.event.GuiDragHandler
import dev.slne.augmented.common.gui.gui.Gui
import dev.slne.augmented.common.gui.position.Position
import dev.slne.augmented.common.gui.position.Positionable
import dev.slne.augmented.common.gui.position.area.areas.CuboidArea
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack

abstract class GuiButton<P : Positionable>(
    override val gui: Gui,
    override val icon: ItemStack,
    override var position: P,
    override var visible: Boolean = true,
    override var enabled: Boolean = true
) : Button<P> {

    override var onDrag: GuiDragHandler? = null
    override var onClick: GuiClickHandler? = null

    override fun onMount() {

    }

    override fun onRemount() {

    }

    override fun onUnmount() {

    }

    override fun mount(inventory: Inventory) {
        if (position is Position) {
            inventory.setItem((position as Position).toAbsolute(), icon)
        } else if (position is CuboidArea) {
            val area = position as CuboidArea

            if (!area.isContainedInOtherArea(gui.area)) {
                throw ArrayIndexOutOfBoundsException("Area out of bounds, $area does not fit in ${gui.area}")
            }

            for (walkingPosition in area.upperLeft..area.bottomRight) {
                inventory.setItem(walkingPosition.toAbsolute(), icon)
            }
        }
    }

    override fun remount(inventory: Inventory) {
        mount(inventory)
    }

    override fun unmount(inventory: Inventory) {
        if (position is Position) {
            inventory.setItem((position as Position).toAbsolute(), null)
        } else if (position is CuboidArea) {
            val area = position as CuboidArea

            if (!area.isContainedInOtherArea(gui.area)) {
                throw ArrayIndexOutOfBoundsException("Area out of bounds, $area does not fit in ${gui.area}")
            }

            for (walkingPosition in area.upperLeft..area.bottomRight) {
                inventory.setItem(walkingPosition.toAbsolute(), null)
            }
        }
    }

}