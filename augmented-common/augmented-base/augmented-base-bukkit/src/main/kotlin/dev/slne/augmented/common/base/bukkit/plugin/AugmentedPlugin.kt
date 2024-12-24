package dev.slne.augmented.common.base.bukkit.plugin

import com.github.shynixn.mccoroutine.folia.SuspendingJavaPlugin
import dev.slne.augmented.common.base.bukkit.instance.AugmentedBukkitInstance
import dev.slne.augmented.common.base.core.instance.AugmentedInstance
import org.bukkit.plugin.java.JavaPlugin

val plugin: AugmentedPlugin
    get() = JavaPlugin.getPlugin(AugmentedPlugin::class.java)

abstract class AugmentedPlugin : SuspendingJavaPlugin() {

    override suspend fun onLoadAsync() {
        AugmentedInstance.AugmentedInstanceHolder.instance = AugmentedBukkitInstance()
    }

    override suspend fun onEnableAsync() {
    }

    override suspend fun onDisableAsync() {
    }
}