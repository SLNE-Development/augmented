import net.minecrell.pluginyml.paper.PaperPluginDescription
import org.gradle.accessors.dm.LibrariesForLibs
import xyz.jpenilla.runpaper.RunPaperExtension

val libs = the<LibrariesForLibs>()

plugins {
    `java-library`

    id("net.minecrell.plugin-yml.paper")
    id("xyz.jpenilla.run-paper")
}

dependencies {
    compileOnlyApi(libs.commandapi.bukkit)
    compileOnlyApi(libs.commandapi.bukkit.kotlin)
    compileOnlyApi(libs.paper.api)

    api(libs.mccoroutine.folia)
    api(libs.mccoroutine.folia.core)

    paperLibrary(libs.kaml)
    paperLibrary(libs.caffeine)
    paperLibrary(libs.caffeine.coroutines)
    paperLibrary(libs.fastutil)
    paperLibrary(libs.coroutines)
    paperLibrary(libs.mariadb)
    paperLibrary(libs.exposed.core)
    paperLibrary(libs.exposed.dao)
    paperLibrary(libs.exposed.jdbc)
    paperLibrary(libs.hikari)
    paperLibrary(libs.advkt)
}

paper {
    name = project.name
    version = rootProject.findProperty("version") as String? ?: "undefined"

    foliaSupported = true
    apiVersion = "1.21"
    authors = listOf("SLNE Development", "Ammo")
    main = "."

    generateLibrariesJson = true
    loader = "dev.slne.augmented.common.base.bukkit.PluginLibrariesLoader"

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
    val mckotlinVersion = libs.versions.mckotlin.server.get()

    minecraftVersion(paperVersion)

    downloadPlugins {
        modrinth("commandapi", commandApiVersion)
        modrinth("packetevents", packetEventsVersion)
        modrinth("mckotlin", mckotlinVersion)
    }
}
