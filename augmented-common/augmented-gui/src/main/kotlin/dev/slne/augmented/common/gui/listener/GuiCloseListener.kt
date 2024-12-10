package dev.slne.augmented.common.gui.listener

import dev.slne.augmented.common.gui.extensions.toGuiOrNull
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryCloseEvent

object GuiCloseListener : Listener {

    @EventHandler
    fun InventoryCloseEvent.onClose() {
        val gui = inventory.toGuiOrNull() ?: return

        gui.close()
    }
}