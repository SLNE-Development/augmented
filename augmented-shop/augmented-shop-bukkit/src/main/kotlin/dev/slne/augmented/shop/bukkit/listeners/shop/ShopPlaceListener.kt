package dev.slne.augmented.shop.bukkit.listeners.shop

import com.github.shynixn.mccoroutine.folia.launch
import dev.slne.augmented.common.base.bukkit.extensions.toPosition
import dev.slne.augmented.shop.api.shopManager
import dev.slne.augmented.shop.bukkit.extensions.CoreShop
import dev.slne.augmented.shop.bukkit.extensions.isShop
import dev.slne.augmented.shop.bukkit.extensions.isShopItem
import dev.slne.augmented.shop.bukkit.plugin
import dev.slne.augmented.shop.core.coreShopManager
import kotlinx.coroutines.Dispatchers
import net.kyori.adventure.sound.Sound
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockPlaceEvent
import org.jetbrains.exposed.sql.transactions.experimental.suspendedTransactionAsync

object ShopPlaceListener : Listener {

    @EventHandler
    fun BlockPlaceEvent.onPlace() {
        if (!itemInHand.isShopItem()) {
            return
        }

        if (block.isShop()) {
            player.sendMessage(
                Component.text(
                    "Es ist bereits ein Shop an dieser Stelle.",
                    NamedTextColor.RED
                )
            )

            isCancelled = true
            return
        }

        coreShopManager.withLocationLock(block.world.uid to block.location.toPosition()) { locked, removeLock ->
            if (locked) {
                player.sendMessage(
                    Component.text(
                        "Es ist bereits ein Shop an dieser Stelle.",
                        NamedTextColor.RED
                    )
                )

                isCancelled = true
                return
            }

            plugin.launch {
                val shop = suspendedTransactionAsync(Dispatchers.IO) {
                    CoreShop(
                        Material.CHEST,
                        player.uniqueId,
                        "test",
                        block.location.world!!,
                        block.location.toPosition()
                    )
                }.await()

                val result = shopManager.addShop(shop)

                if (!result) {
                    player.sendMessage(
                        Component.text(
                            "Es ist bereits ein Shop an dieser Stelle.",
                            NamedTextColor.RED
                        )
                    )

                    removeLock()
                    return@launch
                }

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

                removeLock()
            }
        }
    }
}