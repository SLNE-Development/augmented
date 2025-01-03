package dev.slne.augmented.common.base.core.config

import com.charleskorn.kaml.Yaml
import com.charleskorn.kaml.decodeFromStream
import com.charleskorn.kaml.encodeToStream
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.serializer
import java.nio.file.Path
import kotlin.io.path.*
import kotlin.reflect.KClass

@OptIn(InternalSerializationApi::class)
abstract class ConfigHolder<C : Any>(
    private val clazz: KClass<C>,
    configFolder: Path,
    fileName: String
) {
    val configPath: Path = configFolder.resolve(fileName)

    var config: C? = null
        private set

    init {
        configFolder.createDirectories()

        if (!configPath.exists()) {
            configPath.createFile()
            saveDefaultConfig()
        }

        loadConfig()
    }

    protected abstract fun setDefaultConfig()

    private fun saveDefaultConfig() {
        setDefaultConfig()
        saveConfig()
    }

    fun reloadConfig() {
        loadConfig()
    }

    fun loadConfig() {
        configPath.inputStream().use {
            Yaml.default.decodeFromStream(clazz.serializer(), it)
        }
    }

    fun saveConfig() {
        val config = config ?: error("Config is not set yet")
        configPath.outputStream().use { outputStream ->
            Yaml.default.encodeToStream(clazz.serializer(), config, outputStream)
        }
    }
}