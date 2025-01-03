package dev.slne.augmented.cabin.bukkit

import com.github.shynixn.mccoroutine.folia.SuspendingJavaPlugin
import dev.slne.augmented.cabin.api.InternalCabinInstance
import dev.slne.augmented.cabin.api.internalCabinInstance
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin

val cabinPlugin: CabinBukkitPlugin
    get() = JavaPlugin.getPlugin(CabinBukkitPlugin::class.java)

class CabinBukkitPlugin : SuspendingJavaPlugin() {

    override suspend fun onLoadAsync() {
        InternalCabinInstance.INSTANCE = BukkitInternalCabinInstance()
        internalCabinInstance.onLoad(classLoader)
    }

    override suspend fun onEnableAsync() {
        internalCabinInstance.onEnable()
    }

    override suspend fun onDisableAsync() {
        internalCabinInstance.onDisable()
    }

}