import net.minecrell.pluginyml.paper.PaperPluginDescription
import org.gradle.accessors.dm.LibrariesForLibs

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
    paperLibrary(libs.jakarta.transactions)
    paperLibrary(libs.mariadb)
    paperLibrary(libs.hibernate)
    paperLibrary(libs.hibernate.hikari)
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

        url("https://repo.slne.dev/repository/maven-unsafe/net/craftoriya/packetuxui-bukkit/1.0.1-SNAPSHOT/packetuxui-bukkit-1.0.1-20241224.044819-1-all.jar")
    }
}
