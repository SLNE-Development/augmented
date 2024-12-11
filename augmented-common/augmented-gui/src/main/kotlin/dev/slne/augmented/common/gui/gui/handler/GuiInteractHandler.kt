package dev.slne.augmented.common.gui.gui.handler

import org.bukkit.event.inventory.InventoryInteractEvent

private val noOpInteractHandlerGui: GuiInteractHandler<InventoryInteractEvent> =
    GuiInteractHandler { false }

fun <T : InventoryInteractEvent> noOpInteractHandlerGui(): GuiInteractHandler<T> =
    noOpInteractHandlerGui as GuiInteractHandler<T>

fun interface GuiInteractHandler<T : InventoryInteractEvent> {
    fun handle(event: T): Boolean
}