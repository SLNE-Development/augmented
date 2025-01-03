package dev.slne.augmented.cabin.api.config

import com.charleskorn.kaml.Yaml
import com.charleskorn.kaml.decodeFromStream
import com.charleskorn.kaml.encodeToStream
import kotlinx.serialization.Serializable
import org.jetbrains.annotations.ApiStatus.Internal
import java.io.FileInputStream
import java.io.FileOutputStream

@Internal
@Serializable
data class CabinConfig(
    val extensions: CabinExtensionConfig
)

val cabinConfig: CabinConfig by lazy { loadConfig() }

private fun loadConfig(): CabinConfig {
    FileInputStream("data/config.yml").use { inputStream ->
        if (inputStream.available() == 0) {
            FileOutputStream("data/config.yml").use { outputStream ->
                val newConfig = CabinConfig(CabinExtensionConfig("enter-extension-name"))

                Yaml.default.encodeToStream(
                    newConfig,
                    outputStream
                )

                return newConfig
            }
        }
        
        return Yaml.default.decodeFromStream(inputStream)
    }
}
