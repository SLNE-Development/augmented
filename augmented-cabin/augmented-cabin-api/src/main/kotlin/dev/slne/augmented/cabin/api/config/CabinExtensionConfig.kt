package dev.slne.augmented.cabin.api.config

import kotlinx.serialization.Serializable
import org.jetbrains.annotations.ApiStatus.Internal

@Internal
@Serializable
data class CabinExtensionConfig(
    val enabledExtension: String
)
