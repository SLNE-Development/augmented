package dev.slne.augmented.common.database.core.config.database

import org.jetbrains.annotations.ApiStatus
import org.spongepowered.configurate.objectmapping.ConfigSerializable
import org.spongepowered.configurate.objectmapping.meta.Comment

@ApiStatus.Internal
@ConfigSerializable
data class DatabaseConfig(
    val hostname: String,
    val port: Int,
    val database: String,
    val username: String,
    val password: String,

    @Comment("Show SQL queries in console")
    val showSql: Boolean,

    @Comment("Format SQL queries")
    val formatSql: Boolean,

    @Comment("Use SQL comments")
    val useSqlComments: Boolean,

    @Comment("HikariCP configuration")
    val hikari: DatabaseHikariConfig
) {
    companion object {
        fun default() = DatabaseConfig(
            hostname = "localhost",
            port = 3306,
            database = "augmented",
            username = "root",
            password = "",
            showSql = false,
            formatSql = false,
            useSqlComments = false,
            hikari = DatabaseHikariConfig.default()
        )

    }

    override fun toString(): String {
        return "DatabaseConfig(hostname='$hostname', port=$port, database='$database', username='$username', hikari=$hikari)"
    }
}