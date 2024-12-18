package dev.slne.augmented.shop.bukkit.listeners.shop

import com.github.shynixn.mccoroutine.folia.entityDispatcher
import dev.slne.augmented.common.base.bukkit.plugin.plugin
import dev.slne.augmented.shop.bukkit.extensions.getShop
import dev.slne.augmented.shop.core.CoreShop
import kotlinx.coroutines.withContext
import net.kyori.adventure.sound.Sound
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent

object ShopDestroyListener : Listener {

    @EventHandler
    suspend fun BlockBreakEvent.onBreak() {
        val shop = block.getShop() as CoreShop? ?: return

        shop.delete()

        withContext(plugin.entityDispatcher(player)) {
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