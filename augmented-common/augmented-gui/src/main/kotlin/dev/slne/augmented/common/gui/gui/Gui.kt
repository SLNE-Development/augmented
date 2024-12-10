package dev.slne.augmented.common.gui.gui

import dev.slne.augmented.common.base.core.extensions.mutableObjectSetOf
import dev.slne.augmented.common.gui.gui.handler.GuiInteractHandler
import it.unimi.dsi.fastutil.objects.ObjectSet
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryDragEvent
import org.bukkit.event.inventory.InventoryType
import org.bukkit.inventory.Inventory
import org.jetbrains.annotations.Range

class Gui(
    private var internalTitle: String,

    val width: @Range(from = 1, to = 9) Int,
    val height: @Range(from = 1, to = 6) Int,

    val inventoryType: InventoryType? = null
) {
    var title: Component
        get() = LegacyComponentSerializer.legacySection().deserialize(internalTitle)
        set(value) {
            internalTitle = LegacyComponentSerializer.legacySection().serialize(value)
        }

    var inventory: Inventory? = null

    val onGlobalClick: GuiInteractHandler<InventoryClickEvent>? = null
    val onGlobalDrag: GuiInteractHandler<InventoryDragEvent>? = null

    private val elements: ObjectSet<GuiElement> = mutableObjectSetOf()

    private fun createInventory() {
        if (inventory !== null) return

        inventory = if (inventoryType !== null) {
            Bukkit.createInventory(null, inventoryType, internalTitle)
        } else {
            Bukkit.createInventory(null, width * height, internalTitle)
        }
    }

    private fun mountComponents() {
        elements.forEach { it.onMount?.handle() }
    }

    private fun unmountComponents() {
        elements.forEach { it.onUnmount?.handle() }
    }

    private fun updateComponents() {
        elements.forEach { it.onUpdate?.handle() }
    }

    fun create() {
        createInventory()
        mountComponents()
        updateComponents()
    }

    fun close() {
        closeAllViewers()
        unmountComponents()
    }

    private fun closeViewer(player: Player) {
        inventory?.viewers?.firstOrNull { it is Player && it == player }?.closeInventory()
    }

    private fun closeAllViewers() {
        inventory?.viewers?.forEach { it.closeInventory() }
    }

}