package dev.slne.augmented.common.gui.gui.group

import dev.slne.augmented.common.gui.gui.BasicGui
import dev.slne.augmented.common.gui.gui.Gui
import dev.slne.augmented.common.gui.gui.element.ElementHolder
import dev.slne.augmented.common.gui.gui.element.GuiElement
import dev.slne.augmented.common.gui.position.area.Area

class GuiGroup(override val gui: Gui, val area: Area) : GuiElement, ElementHolder<GuiElement> {

    var group: GuiGroup? = null

    constructor(gui: Gui) : this(gui as BasicGui, gui.area)

}