package dev.slne.augmented.cabin.api

import dev.slne.augmented.cabin.api.config.cabinConfig
import dev.slne.augmented.cabin.api.extension.CabinExtension
import dev.slne.augmented.cabin.api.extension.CabinExtensionManager
import dev.slne.augmented.cabin.api.extension.extensionManager
import java.nio.file.Path

abstract class InternalCabinInstance {

    private var _activeCabin: CabinExtension? = null
    val activeCabin: CabinExtension
        get() = _activeCabin ?: error("No active Cabin extension")

    abstract val dataPath: Path
    abstract fun logInfo(message: String)
    abstract fun logWarn(message: String)
    abstract fun logError(message: String)
    abstract fun shutdownCabinPlugin()

    suspend fun onLoad() {
        CabinExtensionManager.loadExtensions()

        if (CabinExtensionManager.extensions.isEmpty()) {
            logWarn("No extensions found, shutting down Cabin as it cannot operate without extensions")

            shutdownCabinPlugin()
            return
        }

        val config = cabinConfig
        val enabledExtension = config.extensions.enabledExtension

        extensionManager.extensions.find { it.name == enabledExtension }?.let {
            _activeCabin = it
            it.onEnable()
        } ?: run {
            logWarn("Could not find extension with id $enabledExtension, shutting down Cabin as it cannot operate without extensions")

            shutdownCabinPlugin()
            return
        }
    }

    suspend fun onEnable() {
        _activeCabin?.onEnable()
    }

    suspend fun onDisable() {
        _activeCabin?.onDisable()
    }

    companion object {
        lateinit var INSTANCE: InternalCabinInstance
    }

}

val internalCabinInstance: InternalCabinInstance
    get() = InternalCabinInstance.INSTANCE