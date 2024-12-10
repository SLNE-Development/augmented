package dev.slne.augmented.common.gui.gui

import dev.slne.augmented.common.gui.gui.handler.GuiHandler
import dev.slne.augmented.common.gui.gui.handler.GuiInteractHandler
import dev.slne.augmented.common.gui.position.Position
import it.unimi.dsi.fastutil.objects.ObjectArrayList
import it.unimi.dsi.fastutil.objects.ObjectSet
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryDragEvent

interface GuiElement {

    val priority: Priority

    val width: Int
    val height: Int
    val position: Position

    var onMount: GuiHandler?
    var onUnmount: GuiHandler?
    var onUpdate: GuiHandler?

    var onClick: GuiInteractHandler<InventoryClickEvent>?
    var onDrag: GuiInteractHandler<InventoryDragEvent>?

    fun addElement(element: GuiElement): Boolean
    fun removeElement(element: GuiElement): Boolean
    fun getElements(): ObjectSet<GuiElement>
    fun getElements(position: Position): ObjectSet<GuiElement>
    fun getSortedElements(): ObjectArrayList<GuiElement>

    fun fits(element: GuiElement): Boolean

}