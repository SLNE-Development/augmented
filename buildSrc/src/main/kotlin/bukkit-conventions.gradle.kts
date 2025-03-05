import net.minecrell.pluginyml.paper.PaperPluginDescription
import org.gradle.accessors.dm.LibrariesForLibs
import xyz.jpenilla.runpaper.RunPaperExtension

val libs = the<LibrariesForLibs>()

plugins {
    `java-library`

    id("net.minecrell.plugin-yml.paper")
    id("xyz.jpenilla.run-paper")
    id("xyz.jpenilla.gremlin-gradle")
}

dependencies {
    compileOnlyApi(libs.commandapi.bukkit)
    compileOnlyApi(libs.commandapi.bukkit.kotlin)
    compileOnlyApi(libs.paper.api)

    api(libs.mccoroutine.folia)
    api(libs.mccoroutine.folia.core)

    runtimeDownload(libs.kaml) {
        exclude("org.jetbrains.kotlin", "kotlin-stdlib")
        exclude("org.jetbrains.kotlin", "kotlin-reflect")
        exclude("org.jetbrains.kotlinx", "kotlinx-coroutines-core")
        exclude("org.jetbrains.kotlinx", "kotlinx-coroutines-jdk8")
        exclude("org.jetbrains.kotlinx", "kotlinx-serialization-core-jvm")
    }
    runtimeDownload(libs.caffeine) {
        exclude("org.jetbrains.kotlin", "kotlin-stdlib")
        exclude("org.jetbrains.kotlin", "kotlin-reflect")
        exclude("org.jetbrains.kotlinx", "kotlinx-coroutines-core")
        exclude("org.jetbrains.kotlinx", "kotlinx-coroutines-jdk8")
        exclude("org.jetbrains.kotlinx", "kotlinx-serialization-core-jvm")
    }
    runtimeDownload(libs.caffeine.coroutines) {
        exclude("org.jetbrains.kotlin", "kotlin-stdlib")
        exclude("org.jetbrains.kotlin", "kotlin-reflect")
        exclude("org.jetbrains.kotlinx", "kotlinx-coroutines-core")
        exclude("org.jetbrains.kotlinx", "kotlinx-coroutines-jdk8")
        exclude("org.jetbrains.kotlinx", "kotlinx-serialization-core-jvm")
    }
    runtimeDownload(libs.fastutil) {
        exclude("org.jetbrains.kotlin", "kotlin-stdlib")
        exclude("org.jetbrains.kotlin", "kotlin-reflect")
        exclude("org.jetbrains.kotlinx", "kotlinx-coroutines-core")
        exclude("org.jetbrains.kotlinx", "kotlinx-coroutines-jdk8")
        exclude("org.jetbrains.kotlinx", "kotlinx-serialization-core-jvm")
    }
    runtimeDownload(libs.mariadb) {
        exclude("org.jetbrains.kotlin", "kotlin-stdlib")
        exclude("org.jetbrains.kotlin", "kotlin-reflect")
        exclude("org.jetbrains.kotlinx", "kotlinx-coroutines-core")
        exclude("org.jetbrains.kotlinx", "kotlinx-coroutines-jdk8")
        exclude("org.jetbrains.kotlinx", "kotlinx-serialization-core-jvm")
    }
    runtimeDownload(libs.exposed.core) {
        exclude("org.jetbrains.kotlin", "kotlin-stdlib")
        exclude("org.jetbrains.kotlin", "kotlin-reflect")
        exclude("org.jetbrains.kotlinx", "kotlinx-coroutines-core")
        exclude("org.jetbrains.kotlinx", "kotlinx-coroutines-jdk8")
        exclude("org.jetbrains.kotlinx", "kotlinx-serialization-core-jvm")
    }
    runtimeDownload(libs.exposed.dao) {
        exclude("org.jetbrains.kotlin", "kotlin-stdlib")
        exclude("org.jetbrains.kotlin", "kotlin-reflect")
        exclude("org.jetbrains.kotlinx", "kotlinx-coroutines-core")
        exclude("org.jetbrains.kotlinx", "kotlinx-coroutines-jdk8")
        exclude("org.jetbrains.kotlinx", "kotlinx-serialization-core-jvm")
    }
    runtimeDownload(libs.exposed.jdbc) {
        exclude("org.jetbrains.kotlin", "kotlin-stdlib")
        exclude("org.jetbrains.kotlin", "kotlin-reflect")
        exclude("org.jetbrains.kotlinx", "kotlinx-coroutines-core")
        exclude("org.jetbrains.kotlinx", "kotlinx-coroutines-jdk8")
        exclude("org.jetbrains.kotlinx", "kotlinx-serialization-core-jvm")
    }
    runtimeDownload(libs.hikari) {
        exclude("org.jetbrains.kotlin", "kotlin-stdlib")
        exclude("org.jetbrains.kotlin", "kotlin-reflect")
        exclude("org.jetbrains.kotlinx", "kotlinx-coroutines-core")
        exclude("org.jetbrains.kotlinx", "kotlinx-coroutines-jdk8")
        exclude("org.jetbrains.kotlinx", "kotlinx-serialization-core-jvm")
    }
    runtimeDownload(libs.advkt) {
        exclude("org.jetbrains.kotlin", "kotlin-stdlib")
        exclude("org.jetbrains.kotlin", "kotlin-reflect")
        exclude("org.jetbrains.kotlinx", "kotlinx-coroutines-core")
        exclude("org.jetbrains.kotlinx", "kotlinx-coroutines-jdk8")
        exclude("org.jetbrains.kotlinx", "kotlinx-serialization-core-jvm")
    }
}

paper {
    name = project.name
    version = rootProject.findProperty("version") as String? ?: "undefined"

    foliaSupported = true
    apiVersion = "1.21"
    authors = listOf("SLNE Development", "Ammo")
    main = "."

    generateLibrariesJson = false
    loader = "xyz.jpenilla.gremlin.runtime.platformsupport.DefaultsPaperPluginLoader"

    serverDependencies {
        register("CommandAPI") {
            load = PaperPluginDescription.RelativeLoadOrder.BEFORE
            required = true
        }
        register("packetevents") {
            load = PaperPluginDescription.RelativeLoadOrder.BEFORE
            required = true
        }
        register("MCKotlin-Paper") {
            load = PaperPluginDescription.RelativeLoadOrder.BEFORE
            required = true
        }
    }
}

runPaper {
    folia {
        pluginsMode = RunPaperExtension.Folia.PluginsMode.INHERIT_ALL

        registerTask()
    }
}

tasks.runServer {
    val paperVersion = libs.versions.paper.server.get()
    val commandApiVersion = libs.versions.commandapi.server.get()
    val packetEventsVersion = libs.versions.packetevents.server.get()

    minecraftVersion(paperVersion)

    downloadPlugins {
        modrinth("commandapi", commandApiVersion)
        modrinth("packetevents", packetEventsVersion)
//        modrinth("mckotlin", "Z25PwYNh")
    }
}
