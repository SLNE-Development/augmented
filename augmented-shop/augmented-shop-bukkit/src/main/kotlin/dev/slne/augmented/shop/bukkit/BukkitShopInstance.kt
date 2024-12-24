package dev.slne.augmented.shop.bukkit

import dev.slne.augmented.shop.core.ShopInstance
import net.kyori.adventure.text.Component
import net.kyori.adventure.util.Services
import org.bukkit.Bukkit
import java.util.*

object BukkitShopInstance : ShopInstance, Services.Fallback {

    override fun getPlayerDisplayName(uuid: UUID): Component {
        val player = Bukkit.getPlayer(uuid)

        if (player != null) {
            return player.displayName()
        }

        val offlinePlayer = Bukkit.getOfflinePlayer(uuid)

        return Component.text(offlinePlayer.name ?: "Unknown")
    }
}