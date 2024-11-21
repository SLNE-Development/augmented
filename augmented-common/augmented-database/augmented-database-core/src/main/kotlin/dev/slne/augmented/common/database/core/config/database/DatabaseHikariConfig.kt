package dev.slne.augmented.common.database.core.config.database

import org.jetbrains.annotations.ApiStatus.Internal
import org.spongepowered.configurate.objectmapping.ConfigSerializable
import org.spongepowered.configurate.objectmapping.meta.Comment

@Internal
@ConfigSerializable
data class DatabaseHikariConfig(

    @Comment("The minimum number of idle connections that HikariCP tries to maintain in the pool.")
    val minimumIdle: Int,

    @Comment("The maximum number of connections that HikariCP can keep in the pool.")
    val maximumPoolSize: Int,

    @Comment("The maximum amount of time that a connection is allowed to sit idle in the pool.")
    val idleTimeout: Long,

    @Comment("The maximum amount of time that HikariCP waits for a connection to be established.")
    val connectionTimeout: Long,

    @Comment("The maximum lifetime of a connection in the pool.")
    val maxLifetime: Long,

    @Comment("The fully qualified name of the JDBC driver class. Leave this as is if you are using MariaDB.")
    val dataSourceClassName: String,
) {
    companion object {
        fun default() = DatabaseHikariConfig(
            minimumIdle = 10,
            maximumPoolSize = 10,
            idleTimeout = 60000,
            connectionTimeout = 30000,
            maxLifetime = 1800000,
            dataSourceClassName = "org.mariadb.jdbc.MariaDbDataSource"
        )
    }

    override fun toString(): String {
        return "DatabaseHikariConfig(minimumIdle=$minimumIdle, maximumPoolSize=$maximumPoolSize, idleTimeout=$idleTimeout, connectionTimeout=$connectionTimeout, maxLifetime=$maxLifetime, dataSourceClassName='$dataSourceClassName')"
    }
}
