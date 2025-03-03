package dev.slne.augmented.shop.bukkit.listeners.shop

import com.github.shynixn.mccoroutine.folia.entityDispatcher
import com.github.shynixn.mccoroutine.folia.launch
import dev.slne.augmented.common.base.bukkit.extensions.toPosition
import dev.slne.augmented.shop.bukkit.extensions.getShop
import dev.slne.augmented.shop.bukkit.plugin
import dev.slne.augmented.shop.core.CoreShop
import dev.slne.augmented.shop.core.coreShopManager
import ink.pmc.advkt.component.green
import ink.pmc.advkt.component.red
import ink.pmc.advkt.component.text
import ink.pmc.advkt.send
import ink.pmc.advkt.sound.sound
import ink.pmc.advkt.sound.type
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import net.kyori.adventure.sound.Sound.Emitter
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.Sound
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.jetbrains.exposed.sql.transactions.experimental.suspendedTransactionAsync

object ShopDestroyListener : Listener {

    @EventHandler
    fun BlockBreakEvent.onBreak() {
        val beforeLocked =
            coreShopManager.isLocationLocked(block.world.uid, block.location.toPosition())

        if (beforeLocked) {
            player.send {
                text("Es ist gerade ein Shop an dieser Stelle im Aufbau.") with red()
            }

            isCancelled = true
            return
        }

        coreShopManager.withLocationLock(block.world.uid to block.location.toPosition()) { locked, removeLock ->
            val shop = block.getShop() as CoreShop?

            if (locked) {
                player.send {
                    text("Es ist gerade ein Shop an dieser Stelle im Aufb23232332au.") with red()
                }

                isCancelled = true
                return
            }

            if (shop == null) {
                removeLock()
                return
            }

            plugin.launch {
                suspendedTransactionAsync(Dispatchers.IO) {
                    shop.delete()
                }.await()

                withContext(plugin.entityDispatcher(player)) {
                    player.playSound(
                        sound {
                            type(Sound.BLOCK_ANVIL_BREAK)
                        },
                        Emitter.self()
                    )

                    player.send {
                        text("Der Shop ") with green()
                        text(shop.shopKey.toString()) with NamedTextColor.YELLOW
                        text(" wurde zerstört.") with green()
                    }

                    removeLock()
                }
            }
        }
    }
}