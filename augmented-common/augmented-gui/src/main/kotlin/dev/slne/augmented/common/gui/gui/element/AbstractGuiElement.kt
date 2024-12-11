package dev.slne.augmented.common.gui.gui.element

import dev.slne.augmented.common.base.core.extensions.freeze
import dev.slne.augmented.common.base.core.extensions.mutableObjectSetOf
import dev.slne.augmented.common.base.core.extensions.toObjectList
import dev.slne.augmented.common.gui.gui.GuiElement
import dev.slne.augmented.common.gui.gui.Priority
import dev.slne.augmented.common.gui.gui.handler.GuiHandler
import dev.slne.augmented.common.gui.gui.handler.GuiInteractHandler
import dev.slne.augmented.common.gui.gui.handler.noOpHandlerGui
import dev.slne.augmented.common.gui.gui.handler.noOpInteractHandlerGui
import dev.slne.augmented.common.gui.position.Position
import it.unimi.dsi.fastutil.objects.ObjectSet
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryDragEvent

abstract class AbstractGuiElement(
    override val position: Position,
    override val height: Int,
    override val width: Int,
    override val priority: Priority = Priority.NORMAL
) : GuiElement {
    private val elements: ObjectSet<GuiElement> = mutableObjectSetOf()

    override var onMount = noOpHandlerGui
    override var onUnmount = noOpHandlerGui
    override var onUpdate = noOpHandlerGui

    override var onClick = noOpInteractHandlerGui<InventoryClickEvent>()
    override var onDrag = noOpInteractHandlerGui<InventoryDragEvent>()

    override fun onUpdate(handler: GuiHandler) {
        onUpdate = handler
    }

    override fun onUnmount(handler: GuiHandler) {
        onUnmount = handler
    }

    override fun onMount(handler: GuiHandler) {
        onMount = handler
    }

    override fun onDrag(handler: GuiInteractHandler<InventoryDragEvent>) {
        onDrag = handler
    }

    override fun onClick(handler: GuiInteractHandler<InventoryClickEvent>) {
        onClick = handler
    }

    override fun addElement(element: GuiElement) = elements.add(element)
    override fun removeElement(element: GuiElement) = elements.remove(element)
    override fun getElements() = elements.freeze()
    override fun getElements(position: Position) =
        elements.filterTo(mutableObjectSetOf()) { it.position == position }.freeze()

    override fun getSortedElements() =
        elements.sortedWith { a, b -> a.priority.compareTo(b.priority) }.toObjectList()

    override fun fits(element: GuiElement) =
        element.position.x + element.width <= width && element.position.y + element.height <= height
}