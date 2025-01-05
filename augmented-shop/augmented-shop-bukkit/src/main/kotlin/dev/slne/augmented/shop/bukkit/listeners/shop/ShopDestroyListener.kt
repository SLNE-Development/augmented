package dev.slne.augmented.shop.bukkit.listeners.shop

import com.github.shynixn.mccoroutine.folia.entityDispatcher
import com.github.shynixn.mccoroutine.folia.launch
import dev.slne.augmented.common.base.bukkit.extensions.toPosition
import dev.slne.augmented.shop.bukkit.extensions.getShop
import dev.slne.augmented.shop.bukkit.plugin
import dev.slne.augmented.shop.core.CoreShop
import dev.slne.augmented.shop.core.coreShopManager
import net.kyori.adventure.sound.Sound
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent

object ShopDestroyListener : Listener {

    @EventHandler
    fun BlockBreakEvent.onBreak() =
        coreShopManager.withLocationLock(block.location.world.uid to block.location.toPosition()) { locked, removeLock ->
            val shop = block.getShop() as CoreShop?

            if (shop == null) {
                removeLock()
                return@withLocationLock
            }

            if (locked) {
                isCancelled = true
                return@withLocationLock
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

                removeLock()
            }
        }
}