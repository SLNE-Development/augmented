package dev.slne.augmented.common.database.core.config.database

import kotlinx.serialization.Serializable
import org.jetbrains.annotations.ApiStatus

@ApiStatus.Internal
@Serializable
data class DatabaseConfig(
    val hostname: String,
    val port: Int,
    val database: String,
    val username: String,
    val password: String,
    val showSql: Boolean,
    val formatSql: Boolean,
    val useSqlComments: Boolean,
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