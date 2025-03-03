package dev.slne.augmented.shop.bukkit

import com.google.common.io.ByteStreams
import kotlinx.coroutines.CompletableDeferred
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.plugin.Plugin
import org.bukkit.plugin.messaging.PluginMessageListener
import java.net.InetSocketAddress
import java.util.*

typealias ForwardConsumer = (channel: String, player: Player, data: ByteArray) -> Unit

class BungeeChannelApi(private val plugin: Plugin) {

    companion object {
        private val registeredInstances: WeakHashMap<Plugin, BungeeChannelApi> = WeakHashMap()

        fun of(plugin: Plugin): BungeeChannelApi = synchronized(registeredInstances) {
            registeredInstances.computeIfAbsent(plugin) { BungeeChannelApi(plugin) }
        }
    }

    private val messageListener: PluginMessageListener
    private val callbackMap: MutableMap<String, ArrayDeque<CompletableDeferred<*>>> = mutableMapOf()

    private val forwardListeners: MutableMap<String, ForwardConsumer> = HashMap()
    var globalForwardConsumer: ForwardConsumer? = null

    init {
        synchronized(registeredInstances) {
            registeredInstances.compute(plugin) { _, oldInstance ->
                oldInstance?.unregister()
                this
            }
        }

        this.messageListener = PluginMessageListener { channel, player, message ->
            this@BungeeChannelApi.onPluginMessageReceived(
                channel,
                player,
                message
            )
        }

        val messenger = Bukkit.getServer().messenger
        messenger.registerOutgoingPluginChannel(plugin, "BungeeCord")
        messenger.registerIncomingPluginChannel(plugin, "BungeeCord", this.messageListener)
    }

    fun registerForwardListener(consumer: ForwardConsumer) {
        globalForwardConsumer = consumer
    }

    fun registerForwardListener(channel: String, consumer: ForwardConsumer) {
        synchronized(forwardListeners) {
            forwardListeners[channel] = consumer
        }
    }

    fun unregister() {
        val messenger = Bukkit.getServer().messenger

        messenger.unregisterOutgoingPluginChannel(plugin, "BungeeCord")
        messenger.unregisterIncomingPluginChannel(plugin, "BungeeCord", this.messageListener)

        callbackMap.clear()
    }

    private fun computeQueueValue(queueValue: CompletableDeferred<*>): (String, ArrayDeque<CompletableDeferred<*>>?) -> ArrayDeque<CompletableDeferred<*>> {
        return { _, value ->
            (value ?: ArrayDeque()).apply { add(queueValue) }
        }
    }

    private fun getFirstPlayer() = getFirstPlayer0(Bukkit.getOnlinePlayers())
        ?: error("Bungee Messaging requires at least one player to be online.")

    private fun getFirstPlayer0(players: Array<Player>) =
        if (players.isEmpty()) null else players[0]

    private fun getFirstPlayer0(players: Collection<Player>) =
        getFirstPlayer0(players.toTypedArray())

    fun getPlayerCount(serverName: String): CompletableDeferred<Int> {
        val deferred = CompletableDeferred<Int>()
        synchronized(callbackMap) {
            callbackMap.compute("PlayerCount-$serverName", this.computeQueueValue(deferred))
        }

        val output = ByteStreams.newDataOutput()
        output.writeUTF("PlayerCount")
        output.writeUTF(serverName)

        getFirstPlayer().sendPluginMessage(plugin, "BungeeCord", output.toByteArray())

        return deferred
    }

    fun getPlayerList(serverName: String): CompletableDeferred<List<String>> {
        val deferred = CompletableDeferred<List<String>>()
        synchronized(callbackMap) {
            callbackMap.compute("PlayerList-$serverName", this.computeQueueValue(deferred))
        }

        val output = ByteStreams.newDataOutput()
        output.writeUTF("PlayerList")
        output.writeUTF(serverName)

        getFirstPlayer().sendPluginMessage(plugin, "BungeeCord", output.toByteArray())

        return deferred
    }

    fun getServers(): CompletableDeferred<List<String>> {
        val deferred = CompletableDeferred<List<String>>()
        synchronized(callbackMap) {
            callbackMap.compute("GetServers", this.computeQueueValue(deferred))
        }

        val output = ByteStreams.newDataOutput()
        output.writeUTF("GetServers")

        getFirstPlayer().sendPluginMessage(plugin, "BungeeCord", output.toByteArray())

        return deferred
    }

    fun connect(player: Player, serverName: String) {
        val output = ByteStreams.newDataOutput()
        output.writeUTF("Connect")
        output.writeUTF(serverName)

        player.sendPluginMessage(plugin, "BungeeCord", output.toByteArray())
    }

    fun connectOther(playerName: String, serverName: String) {
        val output = ByteStreams.newDataOutput()
        output.writeUTF("ConnectOther")
        output.writeUTF(playerName)
        output.writeUTF(serverName)

        getFirstPlayer().sendPluginMessage(plugin, "BungeeCord", output.toByteArray())
    }

    fun getIp(player: Player): CompletableDeferred<InetSocketAddress> {
        val deferred = CompletableDeferred<InetSocketAddress>()
        synchronized(callbackMap) {
            callbackMap.compute("IP", this.computeQueueValue(deferred))
        }

        val output = ByteStreams.newDataOutput()
        output.writeUTF("IP")

        player.sendPluginMessage(plugin, "BungeeCord", output.toByteArray())

        return deferred
    }

    fun sendComponent(playerName: String, message: Component) {
        val output = ByteStreams.newDataOutput()
        output.writeUTF(GsonComponentSerializer.gson().serialize(message))

        forwardToPlayer("SendComponent", playerName, output.toByteArray())
    }

    fun sendMessage(playerName: String, message: String) {
        val output = ByteStreams.newDataOutput()
        output.writeUTF("Message")
        output.writeUTF(playerName)
        output.writeUTF(message)

        getFirstPlayer().sendPluginMessage(plugin, "BungeeCord", output.toByteArray())
    }

    fun getServer(): CompletableDeferred<String> {
        val deferred = CompletableDeferred<String>()
        synchronized(callbackMap) {
            callbackMap.compute("GetServer", this.computeQueueValue(deferred))
        }

        val output = ByteStreams.newDataOutput()
        output.writeUTF("GetServer")

        getFirstPlayer().sendPluginMessage(plugin, "BungeeCord", output.toByteArray())

        return deferred
    }

    fun getUuid(player: Player): CompletableDeferred<String> {
        val deferred = CompletableDeferred<String>()
        synchronized(callbackMap) {
            callbackMap.compute("UUID", this.computeQueueValue(deferred))
        }

        val output = ByteStreams.newDataOutput()
        output.writeUTF("UUID")

        player.sendPluginMessage(plugin, "BungeeCord", output.toByteArray())

        return deferred
    }

    fun getUuid(playerName: String): CompletableDeferred<String> {
        val deferred = CompletableDeferred<String>()
        synchronized(callbackMap) {
            callbackMap.compute("UUIDOther", this.computeQueueValue(deferred))
        }

        val output = ByteStreams.newDataOutput()
        output.writeUTF("UUIDOther")
        output.writeUTF(playerName)

        getFirstPlayer().sendPluginMessage(plugin, "BungeeCord", output.toByteArray())

        return deferred
    }

    fun getServerIp(serverName: String): CompletableDeferred<InetSocketAddress> {
        val deferred = CompletableDeferred<InetSocketAddress>()
        synchronized(callbackMap) {
            callbackMap.compute("ServerIP-$serverName", this.computeQueueValue(deferred))
        }

        val output = ByteStreams.newDataOutput()
        output.writeUTF("ServerIP")
        output.writeUTF(serverName)

        getFirstPlayer().sendPluginMessage(plugin, "BungeeCord", output.toByteArray())

        return deferred
    }

    fun kickPlayer(playerName: String, message: Component): CompletableDeferred<InetSocketAddress> {
        val deferred = CompletableDeferred<InetSocketAddress>()
        synchronized(callbackMap) {
            callbackMap.compute("KickPlayer-$playerName", this.computeQueueValue(deferred))
        }

        val output = ByteStreams.newDataOutput()
        output.writeUTF("KickPlayer")
        output.writeUTF(playerName)
        output.writeUTF(GsonComponentSerializer.gson().serialize(message))

        getFirstPlayer().sendPluginMessage(plugin, "BungeeCord", output.toByteArray())

        return deferred
    }

    fun forward(channel: String, serverName: String, data: ByteArray) {
        val output = ByteStreams.newDataOutput()
        output.writeUTF("Forward")
        output.writeUTF(serverName)
        output.writeUTF(channel)
        output.writeShort(data.size)
        output.write(data)

        getFirstPlayer().sendPluginMessage(plugin, "BungeeCord", output.toByteArray())
    }

    fun forward(channel: String, data: ByteArray) {
        val output = ByteStreams.newDataOutput()
        output.writeUTF("Forward")
        output.writeUTF("ALL")
        output.writeUTF(channel)
        output.writeShort(data.size)
        output.write(data)

        getFirstPlayer().sendPluginMessage(plugin, "BungeeCord", output.toByteArray())
    }

    fun forwardToPlayer(channel: String, playerName: String, data: ByteArray) {
        val output = ByteStreams.newDataOutput()
        output.writeUTF("ForwardToPlayer")
        output.writeUTF(playerName)
        output.writeUTF(channel)
        output.writeShort(data.size)
        output.write(data)

        getFirstPlayer().sendPluginMessage(plugin, "BungeeCord", output.toByteArray())
    }

    @Suppress("UNCHECKED_CAST")
    private fun onPluginMessageReceived(channel: String, player: Player, message: ByteArray) {
        if (channel != "BungeeCord") return

        val input = ByteStreams.newDataInput(message)
        val subChannel = input.readUTF()

        synchronized(callbackMap) {
            val callbacks: ArrayDeque<CompletableDeferred<*>>?

            if (subChannel.equals("PlayerCount") || subChannel.equals("PlayerList") || subChannel.equals(
                    "UUIDOther"
                ) || subChannel.equals("ServerIP")
            ) {
                val identifier = input.readUTF()
                callbacks = callbackMap["$subChannel-$identifier"]

                if (callbacks.isNullOrEmpty()) {
                    return
                }

                val callback: CompletableDeferred<*> = callbacks.poll()

                try {
                    when (subChannel) {
                        "PlayerCount" -> (callback as CompletableDeferred<Int>).complete(input.readInt())
                        "PlayerList" -> (callback as CompletableDeferred<List<String>>).complete(
                            input.readUTF().split(",").toList()
                        )

                        "UUIDOther" -> (callback as CompletableDeferred<String>).complete(input.readUTF())
                        "ServerIP" -> (callback as CompletableDeferred<InetSocketAddress>).complete(
                            InetSocketAddress(input.readUTF(), input.readUnsignedShort())
                        )

                        else -> {}
                    }

                } catch (e: Exception) {
                    callback.completeExceptionally(e)
                }

                return
            }

            callbacks = callbackMap[subChannel]

            if (callbacks == null) {
                val dataLength = input.readShort().toInt()
                val data = ByteArray(dataLength)
                input.readFully(data)

                globalForwardConsumer?.let { it(channel, player, data) }

                synchronized(forwardListeners) {
                    forwardListeners[subChannel]?.let { it(channel, player, data) }
                }

                return
            }

            if (callbacks.isEmpty()) {
                return
            }

            val callback: CompletableDeferred<*> = callbacks.poll()

            try {
                when (subChannel) {
                    "GetServers" -> (callback as CompletableDeferred<List<String>>).complete(
                        input.readUTF().split(",")
                    )

                    "GetServer", "UUID" -> (callback as CompletableDeferred<String>).complete(input.readUTF())

                    "IP" -> (callback as CompletableDeferred<InetSocketAddress>).complete(
                        InetSocketAddress(input.readUTF(), input.readUnsignedShort())
                    )

                    else -> {}
                }
            } catch (e: Exception) {
                callback.completeExceptionally(e)
            }
        }
    }

}