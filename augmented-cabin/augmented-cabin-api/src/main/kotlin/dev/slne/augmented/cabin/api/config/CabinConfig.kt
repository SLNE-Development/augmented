package dev.slne.augmented.cabin.api.config

import dev.slne.augmented.cabin.api.internalCabinInstance
import dev.slne.augmented.common.base.core.config.ConfigHolder
import kotlinx.serialization.Serializable
import org.jetbrains.annotations.ApiStatus.Internal

@Internal
@Serializable
data class CabinConfig(
    val extensions: CabinExtensionConfig
)

object CabinConfigHolder : ConfigHolder<CabinConfig>(
    clazz = CabinConfig::class,
    configFolder = internalCabinInstance.dataPath,
    fileName = "config.yml"
) {
    override val defaultConfig = CabinConfig(
        extensions = CabinExtensionConfig(
            enabledExtension = "enter-extension-name"
        )
    )
}

val cabinConfig by lazy {
    CabinConfigHolder.config
}