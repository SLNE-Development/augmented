package dev.slne.augmented.cabin.api.extension

import dev.slne.augmented.cabin.api.internalCabinInstance
import dev.slne.augmented.common.base.core.extensions.freeze
import dev.slne.augmented.common.base.core.extensions.mutableObjectSetOf
import dev.slne.augmented.common.base.core.extensions.objectListOf
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.io.File
import java.util.jar.JarFile
import kotlin.io.path.exists

object CabinExtensionManager {

    private val loadMutex = Mutex()
    private val _extensions = mutableObjectSetOf<CabinExtension>()
    val extensions = _extensions.freeze()

    @Suppress("UNCHECKED_CAST")
    operator fun <T : CabinExtension> get(clazz: Class<T>): T? =
        extensions.firstOrNull { clazz.isInstance(it) } as T?

    suspend fun loadExtensions() = loadMutex.withLock {
        _extensions.clear()
        val extensionPath = internalCabinInstance.dataPath.resolve("extensions")

        if (!extensionPath.exists()) {
            extensionPath.toFile().mkdirs()
        }

        extensionPath.toFile().listFiles()?.forEach { file ->
            if (file.extension == "jar") {
                loadExtension(file)
            }
        }
    }

    private fun loadExtension(file: File) {
        val jarFile = JarFile(file)
        val filesHavingCabinAnnotation = objectListOf<File>()

        jarFile.entries().asSequence().forEach { entry ->
            if (entry.name.endsWith(".class")) {
                val className = entry.name.removeSuffix(".class").replace("/", ".")
                val clazz = Class.forName(className)

                if (clazz.isAnnotationPresent(Cabin::class.java)) {
                    filesHavingCabinAnnotation.add(file)
                }
            }
        }

        if (filesHavingCabinAnnotation.isEmpty()) {
            error("No classes with @Cabin annotation found in $file")
        }

        if (filesHavingCabinAnnotation.size > 1) {
            error("Multiple classes with @Cabin annotation found in $file")
        }

        val clazz = Class.forName(
            filesHavingCabinAnnotation.first().name.removeSuffix(".class").replace("/", ".")
        )

        if (!CabinExtension::class.java.isAssignableFrom(clazz)) {
            error("Class $clazz does not extend CabinExtension")
        }

        val emptyConstructor =
            clazz.getDeclaredConstructor() ?: error("No empty constructor found in $clazz")

        val extension = emptyConstructor.newInstance() as CabinExtension
        _extensions.add(extension)

        extension.getDataPath().toFile().mkdirs()
    }

}

val extensionManager = CabinExtensionManager