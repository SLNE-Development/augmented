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
    private val path: Path,
) {

    constructor(
        clazz: KClass<C>,
        configFolder: Path,
        fileName: String,
    ) : this(clazz, configFolder.resolve(fileName))

    abstract val defaultConfig: C

    private lateinit var _config: C
    val config: C
        get() {
            if (!this::_config.isInitialized) {
                error("Config value of config $path is not set yet")
            }

            return _config
        }

    init {
        initConfig()
    }

    private fun initConfig() {
        path.parent.createDirectories()

        if (!path.exists()) {
            path.createFile()
            saveConfig()
        }

        loadConfig()
    }

    fun setDefaultConfig() {
        _config = defaultConfig
    }

    fun loadConfig() {
        path.inputStream().use {
            _config = Yaml.default.decodeFromStream(clazz.serializer(), it)
        }
    }

    fun saveConfig() {
        if (!this::_config.isInitialized) {
            error("Config value of config $path is not set yet")
        }

        path.outputStream().use { outputStream ->
            Yaml.default.encodeToStream(clazz.serializer(), config, outputStream)
        }
    }
}