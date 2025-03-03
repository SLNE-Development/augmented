package dev.slne.augmented.shop.bukkit.listeners.shop

import com.destroystokyo.paper.event.block.BlockDestroyEvent
import dev.slne.augmented.common.base.bukkit.extensions.toPosition
import dev.slne.augmented.shop.bukkit.extensions.isShop
import dev.slne.augmented.shop.core.coreShopManager
import org.bukkit.block.Block
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.block.BlockExplodeEvent
import org.bukkit.event.block.BlockPistonExtendEvent
import org.bukkit.event.block.BlockPistonRetractEvent
import org.bukkit.event.entity.EntityChangeBlockEvent
import org.bukkit.event.entity.EntityExplodeEvent
import org.bukkit.event.player.PlayerInteractEvent

object ShopDestroyPreventionListener : Listener {

    @EventHandler
    fun EntityChangeBlockEvent.onChange() {
        val locked =
            coreShopManager.isLocationLocked(block.world.uid, block.location.toPosition())

        if (locked) {
            isCancelled = true
            return
        }

        if (block.isShop()) {
            isCancelled = true
        }
    }

    @EventHandler
    fun BlockDestroyEvent.onDestroy() {
        val locked =
            coreShopManager.isLocationLocked(block.world.uid, block.location.toPosition())

        if (locked) {
            isCancelled = true
            return
        }

        if (block.isShop()) {
            isCancelled = true
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    fun PlayerInteractEvent.onInteract() {
        if (action != Action.RIGHT_CLICK_BLOCK) return
        val block = clickedBlock ?: return

        val locked =
            coreShopManager.isLocationLocked(block.world.uid, block.location.toPosition())

        if (locked) {
            isCancelled = true
            return
        }

        if (block.isShop()) {
            isCancelled = true
        }
    }

    @EventHandler
    fun BlockPistonExtendEvent.onPistonExtend() {
        checkAndCancelPiston(blocks, ::setCancelled)
    }

    @EventHandler
    fun BlockPistonRetractEvent.onPistonRetract() {
        checkAndCancelPiston(blocks, ::setCancelled)
    }

    @EventHandler
    fun EntityExplodeEvent.onExplode() {
        checkAndCancelExplosion(blockList())
    }

    @EventHandler
    fun BlockExplodeEvent.onExplode() {
        checkAndCancelExplosion(blockList())
    }

    private fun checkAndCancelPiston(blockList: List<Block>, setCancelled: (Boolean) -> Unit) {
        ArrayList(blockList).forEach { block ->
            val locked =
                coreShopManager.isLocationLocked(block.world.uid, block.location.toPosition())

            if (locked) {
                setCancelled(true)
                return
            }

            if (block.isShop()) {
                setCancelled(true)
            }
        }
    }

    private fun checkAndCancelExplosion(blockList: MutableList<Block>) {
        ArrayList(blockList).forEach { block ->
            val locked =
                coreShopManager.isLocationLocked(block.world.uid, block.location.toPosition())

            if (locked) {
                blockList.remove(block)
                return
            }

            if (block.isShop()) {
                blockList.remove(block)
            }
        }
    }
}