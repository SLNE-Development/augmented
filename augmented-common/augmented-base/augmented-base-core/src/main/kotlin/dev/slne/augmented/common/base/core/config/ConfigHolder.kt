package dev.slne.augmented.common.base.core.config

import org.spongepowered.configurate.CommentedConfigurationNode
import org.spongepowered.configurate.hocon.HoconConfigurationLoader
import org.spongepowered.configurate.kotlin.objectMapperFactory
import java.nio.file.Path
import kotlin.reflect.KClass

abstract class ConfigHolder<C>(
    private val clazz: KClass<*>,
    private val path: Path,
    private val fileName: String
) {

    private val loader = HoconConfigurationLoader.builder()
        .defaultOptions { options ->
            options.serializers { builder ->
                builder.registerAnnotatedObjects(
                    objectMapperFactory()
                )
            }
        }
        .path(path.resolve(fileName))
        .build()

    var config: C? = null
        private set

    private var commentedNode: CommentedConfigurationNode? = null

    init {
        if (!path.toFile().exists()) {
            path.toFile().mkdirs()
        }

        if (!path.resolve(fileName).toFile().exists()) {
            saveDefaultConfig()
        }

        loadConfig()
    }

    protected abstract fun setDefaultConfig(commentedConfigurationNode: CommentedConfigurationNode)

    private fun saveDefaultConfig() {
        commentedNode = loader.createNode()
        setDefaultConfig(commentedNode!!)
        loader.save(commentedNode)
    }

    fun reloadConfig() {
        loadConfig()
    }

    @Suppress("UNCHECKED_CAST")
    private fun loadConfig() {
        commentedNode = loader.load()
        config = (commentedNode?.get(clazz.java)
            ?: throw IllegalStateException(
                "${
                    path.resolve(fileName).toAbsolutePath()
                } is either empty or not found."
            )) as C?
    }

}