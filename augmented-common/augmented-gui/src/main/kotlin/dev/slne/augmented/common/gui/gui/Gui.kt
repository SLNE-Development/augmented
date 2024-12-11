package dev.slne.augmented.common.gui.gui

import dev.slne.augmented.common.gui.position.area.areas.CuboidArea
import net.kyori.adventure.text.Component
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory

interface Gui {

    val title: Component
    val area: CuboidArea
    var inventory: Inventory?

    val isMounted: Boolean
    suspend fun mount()
    suspend fun remount()
    suspend fun unmount()

    suspend fun onMount()
    suspend fun onRemount()
    suspend fun onUnmount()

    suspend fun onOpen(player: Player)
    suspend fun onClose(player: Player)

    var parentGui: Gui?
    fun hasParentGui(): Boolean
    fun backToParent(player: Player): Boolean

}