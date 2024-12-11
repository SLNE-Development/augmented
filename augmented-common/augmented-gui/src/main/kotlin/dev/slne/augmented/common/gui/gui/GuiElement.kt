package dev.slne.augmented.common.gui.gui

import dev.slne.augmented.common.gui.gui.handler.GuiHandler
import dev.slne.augmented.common.gui.gui.handler.GuiInteractHandler
import dev.slne.augmented.common.gui.position.Position
import it.unimi.dsi.fastutil.objects.ObjectArrayList
import it.unimi.dsi.fastutil.objects.ObjectList
import it.unimi.dsi.fastutil.objects.ObjectSet
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryDragEvent
import org.jetbrains.annotations.Unmodifiable
import org.jetbrains.annotations.UnmodifiableView

interface GuiElement {

    val priority: Priority

    val width: Int
    val height: Int
    val position: Position

    fun onMount(handler: GuiHandler)
    val onMount: GuiHandler

    fun onUnmount(handler: GuiHandler)
    val onUnmount: GuiHandler
    fun onUpdate(handler: GuiHandler)
    val onUpdate: GuiHandler

    fun onClick(handler: GuiInteractHandler<InventoryClickEvent>)
    val onClick: GuiInteractHandler<InventoryClickEvent>
    fun onDrag(handler: GuiInteractHandler<InventoryDragEvent>)
    val onDrag: GuiInteractHandler<InventoryDragEvent>

    fun addElement(element: GuiElement): Boolean
    fun removeElement(element: GuiElement): Boolean
    fun getElements(): @UnmodifiableView ObjectSet<GuiElement>
    fun getElements(position: Position): @Unmodifiable ObjectSet<GuiElement>
    fun getSortedElements(): @Unmodifiable ObjectList<GuiElement>

    fun fits(element: GuiElement): Boolean
}