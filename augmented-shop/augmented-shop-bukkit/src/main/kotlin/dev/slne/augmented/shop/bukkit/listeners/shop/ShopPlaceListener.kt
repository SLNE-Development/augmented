package dev.slne.augmented.shop.bukkit.listeners.shop

import com.github.shynixn.mccoroutine.folia.entityDispatcher
import dev.slne.augmented.common.base.bukkit.extensions.playSound
import dev.slne.augmented.common.base.bukkit.extensions.sendMessage
import dev.slne.augmented.common.base.bukkit.extensions.toBlockLocation
import dev.slne.augmented.common.base.bukkit.extensions.type
import dev.slne.augmented.common.base.bukkit.plugin.plugin
import dev.slne.augmented.shop.bukkit.extensions.CoreShop
import dev.slne.augmented.shop.bukkit.extensions.isShopItem
import kotlinx.coroutines.withContext
import net.kyori.adventure.sound.Sound
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockPlaceEvent

object ShopPlaceListener : Listener {

    @EventHandler
    suspend fun BlockPlaceEvent.onPlace() {
        if (!itemInHand.isShopItem()) return

        val shop = CoreShop(
            Material.CHEST,
            player.uniqueId,
            "test",
            block.location.world!!,
            block.location.toBlockLocation()
        ).save()

        withContext(plugin.entityDispatcher(player)) {
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