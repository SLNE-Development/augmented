package dev.slne.augmented.common.database.core.config.database

import kotlinx.serialization.Serializable
import org.jetbrains.annotations.ApiStatus.Internal

@Internal
@Serializable
data class DatabaseHikariConfig(

    val minimumIdle: Int,
    val maximumPoolSize: Int,
    val idleTimeout: Long,
    val connectionTimeout: Long,
    val maxLifetime: Long,
    val driverClassName: String,
) {
    companion object {
        fun default() = DatabaseHikariConfig(
            minimumIdle = 10,
            maximumPoolSize = 10,
            idleTimeout = 60000,
            connectionTimeout = 30000,
            maxLifetime = 1800000,
            driverClassName = "org.mariadb.jdbc.Driver"
        )
    }

    override fun toString(): String {
        return "DatabaseHikariConfig(minimumIdle=$minimumIdle, maximumPoolSize=$maximumPoolSize, idleTimeout=$idleTimeout, connectionTimeout=$connectionTimeout, maxLifetime=$maxLifetime, dataSourceClassName='$driverClassName')"
    }
}
