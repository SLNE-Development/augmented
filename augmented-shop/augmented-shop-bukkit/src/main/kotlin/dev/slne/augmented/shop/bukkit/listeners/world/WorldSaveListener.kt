package dev.slne.augmented.shop.bukkit.listeners.world

import dev.slne.augmented.shop.api.shopManager
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.world.WorldSaveEvent

object WorldSaveListener : Listener {

    @EventHandler
    suspend fun WorldSaveEvent.onSave() {
        shopManager.shops.forEach { shop ->
            shop.save()
        }
    }

}