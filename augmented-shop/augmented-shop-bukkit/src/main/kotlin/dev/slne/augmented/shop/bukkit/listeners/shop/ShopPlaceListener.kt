package dev.slne.augmented.shop.bukkit.listeners.shop

import com.github.shynixn.mccoroutine.folia.entityDispatcher
import com.github.shynixn.mccoroutine.folia.launch
import dev.slne.augmented.common.base.bukkit.extensions.toPosition
import dev.slne.augmented.shop.api.shopManager
import dev.slne.augmented.shop.bukkit.extensions.CoreShop
import dev.slne.augmented.shop.bukkit.extensions.isShopItem
import dev.slne.augmented.shop.bukkit.plugin
import dev.slne.augmented.shop.core.CoreShopManager
import net.kyori.adventure.sound.Sound
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockPlaceEvent

object ShopPlaceListener : Listener {

    @EventHandler
    fun BlockPlaceEvent.onPlace() {
        if (!itemInHand.isShopItem()) return
        val shopManager = shopManager as CoreShopManager

        if (shopManager.isLocationLocked(block.location.world.uid, block.location.toPosition())) {
            player.sendMessage("locked place")
            isCancelled = true
            return
        }

        val shop = CoreShop(
            Material.CHEST,
            player.uniqueId,
            "test",
            block.location.world!!,
            block.location.toPosition()
        )

        plugin.launch(plugin.entityDispatcher(player)) {
            shop.save()

            player.playSound(
                Sound.sound().type(org.bukkit.Sound.ENTITY_PLAYER_LEVELUP).build(),
                Sound.Emitter.self()
            )

            player.sendMessage(
                Component.text(
                    "Der Shop ${shop.shopKey} wurde erstellt.",
                    NamedTextColor.GREEN
                )
            )
        }
    }
}