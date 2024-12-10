package dev.slne.augmented.common.gui.gui.element

import dev.slne.augmented.common.base.core.extensions.freeze
import dev.slne.augmented.common.base.core.extensions.mutableObjectSetOf
import dev.slne.augmented.common.base.core.extensions.toObjectArrayList
import dev.slne.augmented.common.base.core.extensions.toObjectSet
import dev.slne.augmented.common.gui.gui.GuiElement
import dev.slne.augmented.common.gui.gui.Priority
import dev.slne.augmented.common.gui.gui.handler.GuiHandler
import dev.slne.augmented.common.gui.gui.handler.GuiInteractHandler
import dev.slne.augmented.common.gui.position.Position
import it.unimi.dsi.fastutil.objects.ObjectArrayList
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

    override var onMount: GuiHandler? = null
    override var onUnmount: GuiHandler? = null
    override var onUpdate: GuiHandler? = null

    override var onClick: GuiInteractHandler<InventoryClickEvent>? = null
    override var onDrag: GuiInteractHandler<InventoryDragEvent>? = null

    override fun addElement(element: GuiElement) = elements.add(element)
    override fun removeElement(element: GuiElement) = elements.remove(element)
    override fun getElements() = elements.freeze()
    override fun getElements(position: Position) =
        elements.filter { it.position == position }.toObjectSet()

    override fun getSortedElements() =
        ObjectArrayList(elements).sortedWith({ a, b -> a.priority.compareTo(b.priority) })
            .toObjectArrayList()

    override fun fits(element: GuiElement) =
        element.position.x + element.width <= width && element.position.y + element.height <= height
}