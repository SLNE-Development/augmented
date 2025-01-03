package dev.slne.augmented.shop.bukkit

import com.github.shynixn.mccoroutine.folia.entityDispatcher
import com.github.shynixn.mccoroutine.folia.registerSuspendingEvents
import dev.slne.augmented.common.database.bukkit.plugin.AugmentedDatabasePlugin
import dev.slne.augmented.common.database.core.persistence.AugmentedPersistence
import dev.slne.augmented.shop.api.shopManager
import dev.slne.augmented.shop.bukkit.commands.ShopCommand
import dev.slne.augmented.shop.bukkit.listeners.shop.ShopClickListener
import dev.slne.augmented.shop.bukkit.listeners.shop.ShopDestroyListener
import dev.slne.augmented.shop.bukkit.listeners.shop.ShopPlaceListener
import dev.slne.augmented.shop.core.CoreShop
import org.bukkit.event.Event
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.plugin.java.JavaPlugin
import kotlin.coroutines.CoroutineContext

internal val plugin: AugmentedShopPlugin
    get() = JavaPlugin.getPlugin(AugmentedShopPlugin::class.java)

class AugmentedShopPlugin : AugmentedDatabasePlugin() {

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
        pluginManager.registerEvents(ShopPlaceListener, this)
        pluginManager.registerEvents(ShopDestroyListener, this)
        pluginManager.registerSuspendingEvents(ShopClickListener, this, eventDispatcher)
    }

    override suspend fun onDisableAsync() {
        super.onDisableAsync()
    }

    private val eventDispatcher = mapOf<Class<out Event>, (event: Event) -> CoroutineContext>(
        PlayerInteractEvent::class.java to {
            require(it is PlayerInteractEvent)
            plugin.entityDispatcher(it.player)
        },
    )
}