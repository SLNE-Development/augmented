package dev.slne.augmented.common.gui.gui.element

import it.unimi.dsi.fastutil.objects.ObjectList

interface ElementHolder<T : GuiElement> {

    fun removeElement(element: T): Boolean

    fun addElement(element: T): Boolean

    fun containsElement(element: T): Boolean

    val elements: ObjectList<T>
}