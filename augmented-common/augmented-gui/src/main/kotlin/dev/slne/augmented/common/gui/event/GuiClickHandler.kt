package dev.slne.augmented.common.gui.event

import dev.slne.augmented.common.gui.gui.Gui
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent

@FunctionalInterface
interface GuiClickHandler {

    fun handle(gui: Gui, player: Player, event: InventoryClickEvent)

}