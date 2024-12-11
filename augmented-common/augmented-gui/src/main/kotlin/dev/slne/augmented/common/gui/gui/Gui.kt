package dev.slne.augmented.common.gui.gui

import dev.slne.augmented.common.base.bukkit.extensions.toLegacy
import dev.slne.augmented.common.base.core.extensions.mutableObjectSetOf
import dev.slne.augmented.common.gui.gui.handler.GuiInteractHandler
import dev.slne.augmented.common.gui.gui.handler.noOpInteractHandlerGui
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryDragEvent
import org.bukkit.event.inventory.InventoryType
import org.bukkit.inventory.Inventory
import org.jetbrains.annotations.Range

const val GUI_MAX_WIDTH = 9L
const val GUI_MAX_HEIGHT = 6L

class Gui(
    private var internalTitle: String,

    val width: @Range(from = 1, to = GUI_MAX_WIDTH) Int,
    val height: @Range(from = 1, to = GUI_MAX_HEIGHT) Int,

    val inventoryType: InventoryType? = null
) {
    init {
        require(width in 1..GUI_MAX_WIDTH) { "Width must be between 1 and $GUI_MAX_WIDTH" }
        require(height in 1..GUI_MAX_HEIGHT) { "Height must be between 1 and $GUI_MAX_HEIGHT" }
    }

    var title: Component
        get() = LegacyComponentSerializer.legacySection().deserialize(internalTitle)
        set(value) {
            internalTitle = value.toLegacy()
        }

    var inventory: Inventory? = null

    val onGlobalClick: GuiInteractHandler<InventoryClickEvent> = noOpInteractHandlerGui()
    val onGlobalDrag: GuiInteractHandler<InventoryDragEvent> = noOpInteractHandlerGui()

    private val elements = mutableObjectSetOf<GuiElement>()

    private fun createInventory() {
        if (inventory != null) return

        inventory = if (inventoryType != null) {
            Bukkit.createInventory(null, inventoryType, internalTitle)
        } else {
            Bukkit.createInventory(null, width * height, internalTitle)
        }
    }

    private fun mountComponents() {
        elements.forEach { it.onMount() }
    }

    private fun unmountComponents() {
        elements.forEach { it.onUnmount() }
    }

    private fun updateComponents() {
        elements.forEach { it.onUpdate() }
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
        inventory?.viewers?.find { it is Player && it == player }?.closeInventory()
    }

    private fun closeAllViewers() {
        inventory?.viewers?.forEach { it.closeInventory() }
    }

}