package dev.slne.augmented.common.database.bukkit.plugin

import dev.slne.augmented.common.base.bukkit.plugin.AugmentedPlugin
import dev.slne.augmented.common.database.bukkit.provider.AugmentedBukkitDataProvider
import dev.slne.augmented.common.database.core.provider.DataProviderHolder
import org.bukkit.plugin.java.JavaPlugin

val databasePlugin: AugmentedDatabasePlugin
    get() = JavaPlugin.getPlugin(AugmentedDatabasePlugin::class.java)

abstract class AugmentedDatabasePlugin : AugmentedPlugin() {

    override suspend fun onLoadAsync() {
        super.onLoadAsync()

        DataProviderHolder.dataProvider = AugmentedBukkitDataProvider
    }

    override suspend fun onEnableAsync() {
        super.onEnableAsync()
    }

    override suspend fun onDisableAsync() {
        super.onDisableAsync()
    }
}