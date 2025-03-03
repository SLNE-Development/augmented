package dev.slne.augmented.shop.bukkit.listeners.shop

import dev.slne.augmented.shop.api.Shop
import dev.slne.augmented.shop.bukkit.extensions.getShop
import dev.slne.augmented.shop.bukkit.menu.ShopMenu
import dev.slne.surf.gui.bukkit.extensions.toUser
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent

object ShopClickListener : Listener {

    @EventHandler(priority = EventPriority.MONITOR)
    suspend fun PlayerInteractEvent.onPlayerInteract() {
        val clickedBlock = clickedBlock ?: return
        val shop = clickedBlock.getShop() ?: return

        when (action) {
            Action.RIGHT_CLICK_BLOCK -> handleShopOpen(shop, player)
            else -> return
        }

        isCancelled = true
    }

    private suspend fun handleShopOpen(shop: Shop, player: Player) {
        player.sendMessage("Shop opened")
        ShopMenu(shop).open(player.toUser())
    }
}