package dev.slne.augmented.common.base.plugin.bukkit

import dev.slne.augmented.common.database.bukkit.plugin.AugmentedDatabasePlugin

class AugmentedBukkitPlugin : AugmentedDatabasePlugin() {

    override suspend fun onLoadAsync() {
        super.onLoadAsync()
    }

    override suspend fun onEnableAsync() {
        super.onEnableAsync()
    }

    override suspend fun onDisableAsync() {
        super.onDisableAsync()
    }
}