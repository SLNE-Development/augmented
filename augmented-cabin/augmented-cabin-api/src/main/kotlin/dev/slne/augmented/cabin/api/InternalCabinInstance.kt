package dev.slne.augmented.cabin.api

import dev.slne.augmented.cabin.api.config.cabinConfig
import dev.slne.augmented.cabin.api.extension.Cabin
import dev.slne.augmented.cabin.api.extension.CabinExtensionManager
import dev.slne.augmented.cabin.api.extension.extensionManager
import java.nio.file.Path
import javax.annotation.OverridingMethodsMustInvokeSuper

abstract class InternalCabinInstance {

    private var loaded: Boolean = false
    private var _activeCabin: Cabin? = null
    val activeCabin: Cabin
        get() = _activeCabin ?: error("No active Cabin extension")

    abstract val dataPath: Path
    abstract fun logInfo(message: String)
    abstract fun logWarn(message: String)
    abstract fun logError(message: String)
    abstract fun shutdownCabinPlugin()

    @OverridingMethodsMustInvokeSuper
    open suspend fun onLoad(classLoader: ClassLoader) {
        CabinExtensionManager.loadExtensions(classLoader)

        if (CabinExtensionManager.extensions.isEmpty()) {
            logWarn("No extensions found, shutting down Cabin as it cannot operate without extensions")

            shutdownCabinPlugin()
            return
        }

        val config = cabinConfig
        val enabledExtension = config.extensions.enabledExtension

        extensionManager.extensions.find { it.name == enabledExtension }?.let {
            _activeCabin = it
            it.onLoad()

            loaded = true
        } ?: run {
            logWarn("Could not find extension with id $enabledExtension, shutting down Cabin as it cannot operate without extensions")

            shutdownCabinPlugin()
            return
        }
    }

    @OverridingMethodsMustInvokeSuper
    open suspend fun onEnable() {
        if (!loaded) {
            return
        }

        _activeCabin?.onEnable()
    }

    @OverridingMethodsMustInvokeSuper
    open suspend fun onDisable() {
        if (!loaded) {
            return
        }

        _activeCabin?.onDisable()
    }

    companion object {
        lateinit var INSTANCE: InternalCabinInstance
    }

}

val internalCabinInstance: InternalCabinInstance
    get() = InternalCabinInstance.INSTANCE