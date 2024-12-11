package dev.slne.augmented.common.gui.listener

import dev.slne.augmented.common.gui.extensions.toGuiOrNull
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent

object GuiClickListener : Listener {

    @EventHandler
    fun InventoryClickEvent.onClick() {
        val gui = inventory.toGuiOrNull() ?: return


    }

}