package dev.slne.augmented.cabin.api.extension

import com.charleskorn.kaml.Yaml
import com.charleskorn.kaml.decodeFromStream
import dev.slne.augmented.cabin.api.internalCabinInstance
import dev.slne.augmented.common.base.core.extensions.freeze
import dev.slne.augmented.common.base.core.extensions.mutableObjectSetOf
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.io.File
import java.nio.file.FileVisitResult
import java.util.jar.JarFile
import java.util.jar.JarInputStream
import kotlin.io.path.ExperimentalPathApi
import kotlin.io.path.createDirectories
import kotlin.io.path.extension
import kotlin.io.path.visitFileTree
import kotlin.reflect.KClass

object CabinExtensionManager {

    private val loadMutex = Mutex()
    private val _extensions = mutableObjectSetOf<Cabin>()
    val extensions = _extensions.freeze()

    @Suppress("UNCHECKED_CAST")
    operator fun <T : Cabin> get(clazz: KClass<T>): T? =
        extensions.firstOrNull { clazz.isInstance(it) } as T?

    @OptIn(ExperimentalPathApi::class)
    suspend fun loadExtensions(classLoader: ClassLoader) = loadMutex.withLock {
        _extensions.clear()
        val extensionPath = internalCabinInstance.dataPath.resolve("extensions")

        extensionPath.createDirectories()
        extensionPath.visitFileTree {
            onVisitFile { path, _ ->
                if (path.extension == "jar") {
                    loadExtension(path.toFile(), classLoader)
                }

                FileVisitResult.CONTINUE
            }
        }
    }

    private fun loadExtension(file: File, classLoader: ClassLoader) {
        val jarFile = JarFile(file)
        val cabinClassLoader = CabinClassLoader(classLoader)

        cabinClassLoader.addURL(file.toURI().toURL())

        val entry = jarFile.getJarEntry("cabin-extension.yml")
            ?: error("No cabin-extension.yml found in $file")

        JarInputStream(jarFile.getInputStream(entry)).use { stream ->
            val config = Yaml.default.decodeFromStream<CabinYmlFile>(stream)
            val mainClass = config.mainClass

            val clazz = Class.forName(mainClass, true, cabinClassLoader)

            if (!Cabin::class.java.isAssignableFrom(clazz)) {
                error("Class $clazz does not extend Cabin")
            }

            val emptyConstructor =
                clazz.getDeclaredConstructor() ?: error("No empty constructor found in $clazz")

            val extension = emptyConstructor.newInstance() as Cabin
            _extensions.add(extension)

            extension.getDataPath().toFile().mkdirs()
        }
    }

}

val extensionManager = CabinExtensionManager