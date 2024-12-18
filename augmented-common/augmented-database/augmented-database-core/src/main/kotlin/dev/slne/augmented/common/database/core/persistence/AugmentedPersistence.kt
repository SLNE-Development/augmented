package dev.slne.augmented.common.database.core.persistence

import dev.slne.augmented.common.database.core.config.DatabaseConfigHolder
import it.unimi.dsi.fastutil.objects.ObjectArraySet
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.hibernate.Session
import org.hibernate.SessionFactory
import org.hibernate.boot.registry.StandardServiceRegistryBuilder
import org.hibernate.cfg.Configuration
import kotlin.reflect.KClass

val sessionFactory = AugmentedPersistence.configureHibernate()

object AugmentedPersistence {

    private val annotatedClasses = ObjectArraySet<KClass<*>>()

    fun addAnnotatedClass(clazz: KClass<*>) {
        annotatedClasses.add(clazz)
    }

    fun configureHibernate(): SessionFactory {
        val config = DatabaseConfigHolder.config!!
        val hikari = config.hikari

        val username = config.username
        val password = config.password
        val hostname = config.hostname
        val port = config.port
        val database = config.database
        val showSql = config.showSql
        val formatSql = config.formatSql
        val useSqlComments = config.useSqlComments

        val minimumIdle = hikari.minimumIdle
        val maximumPoolSize = hikari.maximumPoolSize
        val maxLifetime = hikari.maxLifetime
        val idleTimeout = hikari.idleTimeout
        val connectionTimeout = hikari.connectionTimeout
        val dataSourceClassName = hikari.driverClassName

        val configuration = Configuration()
        configuration.setProperty("hibernate.connection.driver_class", dataSourceClassName)
        configuration.setProperty(
            "hibernate.connection.url",
            "jdbc:mariadb://$hostname:$port/$database"
        )
        configuration.setProperty("hibernate.connection.username", username)
        configuration.setProperty("hibernate.connection.password", password)
        configuration.setProperty("hibernate.hbm2ddl.auto", "none")
        configuration.setProperty(
            "hibernate.transaction.jta.platform",
            "org.hibernate.service.jta.platform.internal.NoJtaPlatform"
        )

        configuration.setProperty("hibernate.show_sql", showSql)
        configuration.setProperty("hibernate.format_sql", formatSql)
        configuration.setProperty("hibernate.use_sql_comments", useSqlComments)

        configuration.setProperty("hibernate.hikari.minimumIdle", minimumIdle)
        configuration.setProperty("hibernate.hikari.maximumPoolSize", maximumPoolSize)
        configuration.setProperty("hibernate.hikari.idleTimeout", "$idleTimeout")
        configuration.setProperty("hibernate.hikari.connectionTimeout", "$connectionTimeout")
        configuration.setProperty("hibernate.hikari.maxLifetime", "$maxLifetime")

        annotatedClasses.forEach {
            configuration.addAnnotatedClass(it.java)
        }

        val serviceRegistry = StandardServiceRegistryBuilder()
            .applySettings(configuration.properties)
            .build()

        return configuration.buildSessionFactory(serviceRegistry)
    }
}


fun <T> Session.upsert(entity: T, persisted: T.() -> Boolean): T {
    if (entity.persisted()) {
        return merge(entity)
    } else {
        persist(entity)

        return entity
    }
}

suspend fun <T> SessionFactory.withSession(block: suspend (session: Session) -> T): T =
    withContext(Dispatchers.IO) {
        val session = openSession()
        session.beginTransaction()
        try {
            val result = block(session)
            session.transaction.commit()
            result
        } catch (e: Exception) {
            session.transaction.rollback()
            throw e
        } finally {
            session.close()
        }
    }

suspend inline fun <reified T> Session.findAll(): List<T> = sessionFactory.withSession { session ->
    val query = session.criteriaBuilder.createQuery(T::class.java)
    val root = query.from(T::class.java)
    session.createQuery(query.select(root)).resultList
}