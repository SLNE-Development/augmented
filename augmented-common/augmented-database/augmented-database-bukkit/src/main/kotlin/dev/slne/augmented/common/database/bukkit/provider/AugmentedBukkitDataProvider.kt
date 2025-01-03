package dev.slne.augmented.common.database.bukkit.provider

import dev.slne.augmented.common.database.bukkit.plugin.AugmentedDatabasePlugin
import dev.slne.augmented.common.database.core.provider.DataProvider
import org.bukkit.plugin.java.JavaPlugin
import java.nio.file.Path

object AugmentedBukkitDataProvider : DataProvider {

    override fun getDataDirectory(): Path =
        JavaPlugin.getPlugin(AugmentedDatabasePlugin::class.java).dataPath
}