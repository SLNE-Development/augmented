package dev.slne.augmented.common.gui.extensions

import dev.slne.augmented.common.gui.GuiManager
import org.bukkit.inventory.Inventory

fun Inventory.toGui() = GuiManager.findGui(this)
fun Inventory.toGuiOrNull() = GuiManager.findGuiOrNull(this)