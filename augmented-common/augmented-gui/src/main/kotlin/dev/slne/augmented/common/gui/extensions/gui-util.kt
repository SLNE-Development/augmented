package dev.slne.augmented.common.gui.extensions

import dev.slne.augmented.common.base.core.extensions.mutableObjectListOf
import dev.slne.augmented.common.gui.gui.Gui
import it.unimi.dsi.fastutil.objects.ObjectArrayList
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor

private val ACTIVE_BREADCRUMB =
    Component.text(">>", NamedTextColor.GRAY)
        .append(Component.text(" %title%", NamedTextColor.GOLD))
private val INACTIVE_BREADCRUMB =
    Component.text(">>", NamedTextColor.GRAY)
        .append(Component.text(" %title%", NamedTextColor.GRAY))

fun getAllGuiTitles(gui: Gui): ObjectArrayList<Component> {
    val titles: ObjectArrayList<Component> = mutableObjectListOf()

    titles.add(gui.title)

    var parentGui = gui.parentGui
    while (parentGui != null) {
        titles.add(parentGui.title)

        parentGui = parentGui.parentGui
    }

    return titles
}

fun getBreadcrumbComponent(gui: Gui): Component {
    val builder = Component.text()
    val titles = getAllGuiTitles(gui)

    for (i in 0 until titles.size) {
        val title = titles[i]

        if (i == 0) {
            builder.append(ACTIVE_BREADCRUMB.replaceText {
                it.match("%title%").replacement(title)
            })
        } else {
            builder.appendNewline()
            builder.append(INACTIVE_BREADCRUMB.replaceText {
                it.match("%title%").replacement(title)
            })
        }
    }

    return builder.build()
}