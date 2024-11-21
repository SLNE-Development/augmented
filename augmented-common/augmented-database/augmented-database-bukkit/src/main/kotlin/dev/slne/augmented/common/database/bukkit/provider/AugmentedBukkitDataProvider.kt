package dev.slne.augmented.common.database.bukkit.provider

import dev.slne.augmented.common.database.bukkit.plugin.databasePlugin
import dev.slne.augmented.common.database.core.provider.DataProvider
import java.nio.file.Path

object AugmentedBukkitDataProvider : DataProvider {

    override fun getDataDirectory(): Path = databasePlugin.dataFolder.toPath()
}