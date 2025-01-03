package dev.slne.augmented.common.database.core.config

import dev.slne.augmented.common.base.core.config.ConfigHolder
import dev.slne.augmented.common.database.core.config.database.DatabaseConfig
import dev.slne.augmented.common.database.core.provider.DataProviderHolder

object DatabaseConfigHolder : ConfigHolder<DatabaseConfig>(
    DatabaseConfig::class, DataProviderHolder.dataProvider.getDataDirectory(),
    "database-config.yml"
) {

    override val defaultConfig = DatabaseConfig.default()
}