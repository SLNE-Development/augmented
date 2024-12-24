package dev.slne.augmented.shop.bukkit

import com.github.shynixn.mccoroutine.folia.entityDispatcher
import com.github.shynixn.mccoroutine.folia.regionDispatcher
import com.github.shynixn.mccoroutine.folia.registerSuspendingEvents
import dev.slne.augmented.common.base.bukkit.plugin.AugmentedPlugin
import dev.slne.augmented.common.base.bukkit.plugin.plugin
import dev.slne.augmented.common.database.core.persistence.AugmentedPersistence
import dev.slne.augmented.shop.api.shopManager
import dev.slne.augmented.shop.bukkit.commands.ShopCommand
import dev.slne.augmented.shop.bukkit.listeners.shop.ShopClickListener
import dev.slne.augmented.shop.bukkit.listeners.shop.ShopDestroyListener
import dev.slne.augmented.shop.bukkit.listeners.shop.ShopPlaceListener
import dev.slne.augmented.shop.bukkit.listeners.world.WorldSaveListener
import dev.slne.augmented.shop.core.CoreShop
import org.bukkit.event.Event
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.block.BlockPlaceEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.world.WorldSaveEvent
import kotlin.coroutines.CoroutineContext

class AugmentedShopPlugin : AugmentedPlugin() {

    override suspend fun onLoadAsync() {
        super.onLoadAsync()

        AugmentedPersistence.addAnnotatedClass(CoreShop::class)

        shopManager.fetchShops()
    }

    override suspend fun onEnableAsync() {
        super.onEnableAsync()

        // Commands
        ShopCommand

        // Listeners
        val pluginManager = server.pluginManager
        pluginManager.registerSuspendingEvents(ShopPlaceListener, this, eventDispatcher)
        pluginManager.registerSuspendingEvents(ShopDestroyListener, this, eventDispatcher)
        pluginManager.registerSuspendingEvents(ShopClickListener, this, eventDispatcher)

        pluginManager.registerSuspendingEvents(WorldSaveListener, this, eventDispatcher)
    }

    override suspend fun onDisableAsync() {
        super.onDisableAsync()
    }

    private val eventDispatcher = mapOf<Class<out Event>, (event: Event) -> CoroutineContext>(
        BlockPlaceEvent::class.java to {
            require(it is BlockPlaceEvent)
            plugin.regionDispatcher(it.block.location)
        },
        BlockBreakEvent::class.java to {
            require(it is BlockBreakEvent)
            plugin.regionDispatcher(it.block.location)
        },
        WorldSaveEvent::class.java to {
            require(it is WorldSaveEvent)
            plugin.regionDispatcher(it.world.spawnLocation)
        },
        PlayerInteractEvent::class.java to {
            require(it is PlayerInteractEvent)
            plugin.entityDispatcher(it.player)
        }
    )
}