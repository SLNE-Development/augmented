package dev.slne.augmented.shop.bukkit

import com.github.shynixn.mccoroutine.folia.entityDispatcher
import com.github.shynixn.mccoroutine.folia.registerSuspendingEvents
import com.google.common.io.ByteStreams
import dev.slne.augmented.common.database.bukkit.plugin.AugmentedDatabasePlugin
import dev.slne.augmented.shop.api.shopManager
import dev.slne.augmented.shop.bukkit.commands.ShopCommand
import dev.slne.augmented.shop.bukkit.listeners.shop.ShopClickListener
import dev.slne.augmented.shop.bukkit.listeners.shop.ShopDestroyListener
import dev.slne.augmented.shop.bukkit.listeners.shop.ShopDestroyPreventionListener
import dev.slne.augmented.shop.bukkit.listeners.shop.ShopPlaceListener
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer
import org.bukkit.event.Event
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.plugin.java.JavaPlugin
import kotlin.coroutines.CoroutineContext

internal val plugin: AugmentedShopPlugin
    get() = JavaPlugin.getPlugin(AugmentedShopPlugin::class.java)

class AugmentedShopPlugin : AugmentedDatabasePlugin() {

    lateinit var bungeeChannelApi: BungeeChannelApi

    override suspend fun onLoadAsync() {
        super.onLoadAsync()

        shopManager.fetchShops()
    }

    override suspend fun onEnableAsync() {
        super.onEnableAsync()

        bungeeChannelApi = BungeeChannelApi.of(this)

        // Commands
        ShopCommand

        // Listeners
        val pluginManager = server.pluginManager
        pluginManager.registerEvents(ShopPlaceListener, this)
        pluginManager.registerEvents(ShopDestroyListener, this)
        pluginManager.registerEvents(ShopDestroyPreventionListener, this)
        pluginManager.registerSuspendingEvents(ShopClickListener, this, eventDispatcher)

        bungeeChannelApi.registerForwardListener("SendComponent") { _, player, data ->
            val input = ByteStreams.newDataInput(data)
            val component = GsonComponentSerializer.gson().deserialize(input.readUTF())

            player.sendMessage(component)
        }
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