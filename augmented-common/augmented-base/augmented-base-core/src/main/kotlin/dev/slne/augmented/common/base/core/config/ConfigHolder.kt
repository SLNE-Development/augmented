package dev.slne.augmented.common.base.core.config

import com.charleskorn.kaml.Yaml
import com.charleskorn.kaml.decodeFromStream
import com.charleskorn.kaml.encodeToStream
import java.io.FileInputStream
import java.io.FileOutputStream
import java.nio.file.Path
import kotlin.reflect.KClass

abstract class ConfigHolder<C>(
    private val clazz: KClass<*>,
    private val path: Path,
    private val fileName: String
) {

    var config: C? = null
        private set


    init {
        if (!path.toFile().exists()) {
            path.toFile().mkdirs()
        }

        if (!path.resolve(fileName).toFile().exists()) {
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
        FileInputStream(path.resolve(fileName).toFile()).use { inputStream ->
            config = Yaml.default.decodeFromStream<C>(inputStream)
        }
    }

    fun saveConfig() {
        FileOutputStream(path.resolve(fileName).toFile()).use { outputStream ->
            Yaml.default.encodeToStream(config, outputStream)
        }
    }

}