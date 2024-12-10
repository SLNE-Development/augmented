package dev.slne.augmented.common.gui.gui.handler

import org.bukkit.event.inventory.InventoryInteractEvent

interface GuiInteractHandler<T : InventoryInteractEvent> {
    fun handle(event: T): Boolean
}