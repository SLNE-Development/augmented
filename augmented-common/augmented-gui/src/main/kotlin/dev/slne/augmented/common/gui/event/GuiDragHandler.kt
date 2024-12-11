package dev.slne.augmented.common.gui.event

import dev.slne.augmented.common.gui.gui.Gui
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryDragEvent

fun interface GuiDragHandler {

    fun handle(gui: Gui, player: Player, event: InventoryDragEvent)

}