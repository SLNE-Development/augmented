package dev.slne.augmented.common.gui.gui

import dev.slne.augmented.common.gui.GuiManager
import dev.slne.augmented.common.gui.button.GuiButton
import dev.slne.augmented.common.gui.gui.element.GuiElement
import dev.slne.augmented.common.gui.position.Position
import dev.slne.augmented.common.gui.position.area.areas.CuboidArea
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer
import org.bukkit.Bukkit.createInventory
import org.bukkit.inventory.ItemStack


abstract class BasicGui<T : GuiElement>(
    override val title: Component,
    override val area: CuboidArea
) : AbstractGui<T>(title, area) {

    override suspend fun mount() {
        if (isMounted) return

        createInventory()
        onMount()
    }

    override suspend fun remount() {
        if (!isMounted) return

        onRemount()
        elements.forEach { remountElement(it) }
    }

    private fun createInventory() {
        inventory = createInventory(
            null,
            area.getSize(),
            LegacyComponentSerializer.legacySection().serialize(title)
        )
    }

    private fun remountElement(element: T) {
        checkElement(element)
        if (!isMounted) return

        if (element is GuiButton<*>) {
            val icon = GuiManager.bindButton(element.icon, element)
            val position = element.position

            if (position is Position) {
                mountInPosition(position, icon, element)
            } else if (position is CuboidArea) {
                mountInArea(position, icon, element)
            }
        }
    }

    private fun mountInArea(area: CuboidArea, itemStack: ItemStack, button: GuiButton<*>) {
        if (!area.isContainedInOtherArea(this.area)) {
            throw ArrayIndexOutOfBoundsException("Area out of bounds, $area does not fit in ${this.area}")
        }

        if (inventory == null) {
            throw IllegalStateException("Inventory is null")
        }

        val fromX = area.upperLeft.x
        val toX = area.bottomRight.x
        val fromY = area.upperLeft.y
        val toY = area.bottomRight.y

        for (x in fromX..toX) {
            for (y in fromY..toY) {
                val slot = Position(x, y).toAbsolute()
                val current = inventory!!.getItem(slot)
                val otherIcon = if (current != null) ItemStack(current) else null

                if (otherIcon != null && GuiManager.getButtonFromIcon(otherIcon) === button) {
                    inventory!!.setItem(slot, itemStack)
                }
            }
        }
    }

    private fun mountInPosition(position: Position, itemStack: ItemStack, button: GuiButton<*>) {
        if (inventory == null) {
            throw IllegalStateException("Inventory is null")
        }

        if (!position.fits(inventory!!)) {
            throw ArrayIndexOutOfBoundsException(position.toAbsolute())
        }

        val slot = position.toAbsolute()
        val current = inventory!!.getItem(slot)
        val otherIcon = if (current != null) ItemStack(current) else null

        if (otherIcon != null && GuiManager.getButtonFromIcon(otherIcon) === button) {
            inventory!!.setItem(slot, itemStack)
        }
    }

}