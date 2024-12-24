import net.minecrell.pluginyml.paper.PaperPluginDescription

plugins {
    `java-library`

    id("net.minecrell.plugin-yml.paper")
    id("xyz.jpenilla.run-paper")
}

paper {
    name = project.name
    version = rootProject.findProperty("version") as String? ?: "1.0.0-SNAPSHOT"

    foliaSupported = false
    apiVersion = "1.21"
    authors = listOf("SLNE Development", "Ammo")

    serverDependencies {
        register("CommandAPI") {
            load = PaperPluginDescription.RelativeLoadOrder.BEFORE
            required = true
        }
        register("augmented-base-plugin-bukkit") {
            load = PaperPluginDescription.RelativeLoadOrder.BEFORE
            required = true
            joinClasspath = true
        }
        register("packetuxui-bukkit") {
            load = PaperPluginDescription.RelativeLoadOrder.BEFORE
            required = true
            joinClasspath = true
        }
        register("packetevents") {
            load = PaperPluginDescription.RelativeLoadOrder.BEFORE
            required = true
            joinClasspath = true
        }
    }
}

tasks.runServer {
    minecraftVersion("1.21.1")

    pluginJars.from(
        project(":augmented-common:augmented-base:augmented-base-plugins:augmented-base-plugin-bukkit").tasks.named(
            "shadowJar"
        )
    )

    downloadPlugins {
        modrinth("commandapi", "9.6.0")
        modrinth("packetevents", "2.7.0")
    }
}