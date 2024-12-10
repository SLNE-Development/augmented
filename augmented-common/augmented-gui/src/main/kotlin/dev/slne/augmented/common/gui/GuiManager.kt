package dev.slne.augmented.common.gui

import dev.slne.augmented.common.base.core.extensions.freeze
import dev.slne.augmented.common.base.core.extensions.mutableObjectSetOf
import dev.slne.augmented.common.gui.gui.Gui
import dev.slne.augmented.common.gui.listener.GuiCloseListener
import it.unimi.dsi.fastutil.objects.ObjectSet
import org.bukkit.inventory.Inventory
import org.bukkit.plugin.java.JavaPlugin

object GuiManager {

    private val guis: ObjectSet<Gui> = mutableObjectSetOf()

    fun register(gui: Gui) = guis.add(gui)
    fun unregister(gui: Gui) = guis.remove(gui)

    fun getGuis() = guis.freeze()

    fun findGui(inventory: Inventory) =
        findGuiOrNull(inventory) ?: throw IllegalArgumentException("Inventory is not a GUI")

    fun findGuiOrNull(inventory: Inventory) = guis.firstOrNull { it.inventory == inventory }

    fun registerListeners(plugin: JavaPlugin) {
        val pluginManager = plugin.server.pluginManager

        pluginManager.registerEvents(GuiCloseListener, plugin)
    }
}