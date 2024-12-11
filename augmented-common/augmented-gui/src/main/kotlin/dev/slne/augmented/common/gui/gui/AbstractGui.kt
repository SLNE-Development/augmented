package dev.slne.augmented.common.gui.gui

import dev.slne.augmented.common.base.core.event.AbstractHandleable
import dev.slne.augmented.common.base.core.extensions.freeze
import dev.slne.augmented.common.base.core.extensions.mutableObjectListOf
import dev.slne.augmented.common.base.core.extensions.synchronize
import dev.slne.augmented.common.gui.event.handler.GuiListener
import dev.slne.augmented.common.gui.extensions.closeGui
import dev.slne.augmented.common.gui.extensions.showGui
import dev.slne.augmented.common.gui.gui.element.ElementHolder
import dev.slne.augmented.common.gui.gui.element.GuiElement
import dev.slne.augmented.common.gui.position.area.areas.CuboidArea
import it.unimi.dsi.fastutil.objects.ObjectList
import net.kyori.adventure.text.Component
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory

abstract class AbstractGui<T : GuiElement>(
    override val title: Component,
    override val area: CuboidArea
) : Gui, ElementHolder<T> {

    private val _elements: ObjectList<T> = mutableObjectListOf<T>().synchronize()
    override val elements = _elements.freeze()

    override var inventory: Inventory? = null
    override val isMounted
        get() = inventory != null

    override var parentGui: Gui? = null

    override suspend fun unmount() {
        onUnmount()

        while (0 < listeners.size) {
            removeListener(listeners.first())
        }

        while (0 < elements.size) {
            val element = elements.first()

            removeElement(element)
            element.onUnmount()
        }

        inventory = null
    }

    override suspend fun onMount() {

    }

    override suspend fun onRemount() {

    }

    override suspend fun onUnmount() {
    }

    override suspend fun onOpen(player: Player) {
    }

    override suspend fun onClose(player: Player) {

    }

    protected fun checkElement(element: T) {
        require(element.gui == this) {
            "Element $element is not part of this gui"
        }
    }

    override fun removeElement(element: T): Boolean {
        checkElement(element)

        if (!elements.contains(element)) {
            return false
        }

        element.enabled = false
        element.visible = false

        return _elements.remove(element)
    }

    override fun addElement(element: T): Boolean {
        checkElement(element)

        return _elements.add(element)
    }

    override fun containsElement(element: T) = elements.contains(element)

    override fun hasParentGui() = parentGui != null

    override fun backToParent(player: Player): Boolean {
        if (hasParentGui()) {
            player.showGui(parentGui!!)

            return true
        } else {
            player.closeGui()

            return false
        }
    }

}