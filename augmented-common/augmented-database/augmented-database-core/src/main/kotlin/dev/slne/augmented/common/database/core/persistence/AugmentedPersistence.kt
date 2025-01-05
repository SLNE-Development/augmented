package dev.slne.augmented.common.database.core.persistence

import dev.slne.augmented.common.database.core.config.DatabaseConfigHolder
import it.unimi.dsi.fastutil.objects.ObjectArraySet
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.hibernate.Session
import org.hibernate.SessionFactory
import org.hibernate.boot.registry.BootstrapServiceRegistryBuilder
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
        val config = DatabaseConfigHolder.config

        val (hostname, port, database, username, password, showSql, formatSql, useSqlComments, hikari) = config
        val (minimumIdle, maximumPoolSize, idleTimeout, connectionTimeout, maxLifetime, driverClassName) = hikari
        
        val registry =
            BootstrapServiceRegistryBuilder().applyClassLoader(this::class.java.classLoader).build()
        val configuration = Configuration(registry).apply {
            setProperty("hibernate.connection.driver_class", driverClassName)
            setProperty(
                "hibernate.connection.url",
                "jdbc:mariadb://$hostname:$port/$database"
            )
            setProperty("hibernate.connection.username", username)
            setProperty("hibernate.connection.password", password)
            setProperty("hibernate.hbm2ddl.auto", "none")
            setProperty(
                "hibernate.transaction.jta.platform",
                "org.hibernate.service.jta.platform.internal.NoJtaPlatform"
            )

            setProperty("hibernate.show_sql", showSql)
            setProperty("hibernate.format_sql", formatSql)
            setProperty("hibernate.use_sql_comments", useSqlComments)

            setProperty("hibernate.hikari.minimumIdle", minimumIdle)
            setProperty("hibernate.hikari.maximumPoolSize", maximumPoolSize)
            setProperty("hibernate.hikari.idleTimeout", "$idleTimeout")
            setProperty("hibernate.hikari.connectionTimeout", "$connectionTimeout")
            setProperty("hibernate.hikari.maxLifetime", "$maxLifetime")

            annotatedClasses.forEach {
                addAnnotatedClass(it.java)
            }
        }

        val serviceRegistry = StandardServiceRegistryBuilder(registry)
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