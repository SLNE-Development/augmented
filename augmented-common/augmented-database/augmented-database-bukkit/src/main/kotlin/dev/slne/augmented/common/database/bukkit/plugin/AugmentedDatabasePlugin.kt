package dev.slne.augmented.common.database.bukkit.plugin

import com.github.shynixn.mccoroutine.folia.SuspendingJavaPlugin
import dev.slne.augmented.common.database.bukkit.provider.AugmentedBukkitDataProvider
import dev.slne.augmented.common.database.core.provider.DataProviderHolder

abstract class AugmentedDatabasePlugin : SuspendingJavaPlugin() {

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