package dev.slne.augmented.common.database.core.config

import dev.slne.augmented.common.base.core.config.ConfigHolder
import dev.slne.augmented.common.database.core.config.database.DatabaseConfig
import dev.slne.augmented.common.database.core.provider.DataProviderHolder.dataProvider
import org.spongepowered.configurate.CommentedConfigurationNode

object DatabaseConfigHolder :
    ConfigHolder<DatabaseConfig>(
        DatabaseConfig::class, dataProvider.getDataDirectory(),
        "database-config.hocon"
    ) {

    override fun setDefaultConfig(commentedConfigurationNode: CommentedConfigurationNode) {
        commentedConfigurationNode.set(DatabaseConfig.default())
    }
}