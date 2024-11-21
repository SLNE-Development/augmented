package dev.slne.augmented.common.base.bukkit.plugin

import com.github.shynixn.mccoroutine.bukkit.SuspendingJavaPlugin
import dev.slne.augmented.common.base.bukkit.instance.AugmentedBukkitInstance
import dev.slne.augmented.common.base.core.instance.AugmentedInstance
import net.kyori.adventure.platform.bukkit.BukkitAudiences
import org.bukkit.plugin.java.JavaPlugin

val plugin: AugmentedPlugin
    get() = JavaPlugin.getPlugin(AugmentedPlugin::class.java)

abstract class AugmentedPlugin : SuspendingJavaPlugin() {

    private lateinit var audiences: BukkitAudiences

    override suspend fun onLoadAsync() {
        AugmentedInstance.AugmentedInstanceHolder.instance = AugmentedBukkitInstance()
    }

    override suspend fun onEnableAsync() {
        audiences = BukkitAudiences.create(this)
    }

    override suspend fun onDisableAsync() {
        audiences.close()
    }
}