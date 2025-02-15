package dev.slne.augmented.common.database.core.persistence

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import dev.slne.augmented.common.database.core.config.DatabaseConfigHolder
import org.jetbrains.exposed.sql.Database

object AugmentedPersistence {

    private lateinit var connection: Database

    fun connectDatabase() {
        val (hostname, port, database, username, password, hikari) = DatabaseConfigHolder.config
        val (minimumIdle, maximumPoolSize, idleTimeout, connectionTimeout, maxLifetime, driverClassName) = hikari

        val hikariConfig = HikariConfig().apply {
            this.jdbcUrl = "jdbc:mariadb://$hostname:$port/$database"
            this.username = username
            this.password = password
            this.minimumIdle = minimumIdle
            this.maximumPoolSize = maximumPoolSize
            this.idleTimeout = idleTimeout
            this.connectionTimeout = connectionTimeout
            this.maxLifetime = maxLifetime
            this.driverClassName = driverClassName
            this.isAutoCommit = false
            this.transactionIsolation = "TRANSACTION_REPEATABLE_READ"

            validate()
        }
        val hikariDataSource = HikariDataSource(hikariConfig)

        connection = Database.connect(hikariDataSource)
    }

    fun disconnectDatabase() {
        connection.connector().close()
    }

}