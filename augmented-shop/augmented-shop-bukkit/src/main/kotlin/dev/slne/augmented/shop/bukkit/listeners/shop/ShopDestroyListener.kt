package dev.slne.augmented.shop.bukkit.listeners.shop

import com.github.shynixn.mccoroutine.folia.entityDispatcher
import com.github.shynixn.mccoroutine.folia.launch
import dev.slne.augmented.common.base.bukkit.extensions.toPosition
import dev.slne.augmented.shop.api.shopManager
import dev.slne.augmented.shop.bukkit.extensions.getShop
import dev.slne.augmented.shop.bukkit.plugin
import dev.slne.augmented.shop.core.CoreShop
import dev.slne.augmented.shop.core.CoreShopManager
import net.kyori.adventure.sound.Sound
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent

object ShopDestroyListener : Listener {

    @EventHandler
    fun BlockBreakEvent.onBreak() {
        val shop = block.getShop() as CoreShop? ?: return
        val shopManager = shopManager as CoreShopManager

        if (shopManager.isLocationLocked(block.location.world.uid, block.location.toPosition())) {
            player.sendMessage("locked break")
            isCancelled = true
            return
        }

        plugin.launch(plugin.entityDispatcher(player)) {
            shop.delete()

            player.playSound(
                Sound.sound().type(org.bukkit.Sound.BLOCK_ANVIL_BREAK).build(),
                Sound.Emitter.self()
            )

            player.sendMessage(
                Component.text(
                    "Der Shop ${shop.shopKey} wurde gelöscht.",
                    NamedTextColor.GREEN
                )
            )
        }
    }
}