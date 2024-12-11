package dev.slne.augmented.common.gui.gui.element

import dev.slne.augmented.common.gui.gui.Gui
import org.bukkit.inventory.Inventory

interface GuiElement {

    fun onMount()
    fun onRemount()
    fun onUnmount()

    fun mount(inventory: Inventory)
    fun remount(inventory: Inventory)
    fun unmount(inventory: Inventory)

    val gui: Gui

    var visible: Boolean
    var enabled: Boolean

}