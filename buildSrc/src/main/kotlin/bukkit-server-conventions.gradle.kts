plugins {
    `java-library`

    id("net.minecrell.plugin-yml.bukkit")
    id("xyz.jpenilla.run-paper")
}

bukkit {
    name = project.name
    version = findProperty("version") as String? ?: "1.0.0-SNAPSHOT"

    foliaSupported = false
    apiVersion = "1.21"
    authors = listOf("SLNE Development", "Ammo")

    depend = listOf("CommandAPI")
}

tasks.runServer {
    minecraftVersion("1.21.1")

    downloadPlugins {
        modrinth("commandapi", "9.6.0")
    }
}