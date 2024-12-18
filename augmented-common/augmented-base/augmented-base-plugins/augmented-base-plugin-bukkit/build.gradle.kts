import net.minecrell.pluginyml.paper.PaperPluginDescription

plugins {
    `common-conventions`
    `shadow-conventions`
    `bukkit-conventions`

    id("net.minecrell.plugin-yml.paper")
}

dependencies {
    api(project(":augmented-common:augmented-base:augmented-base-bukkit"))
    api(project(":augmented-common:augmented-database:augmented-database-bukkit"))
    api(libs.mccoroutine.folia)
    api(libs.mccoroutine.folia.core)
}

paper {
    name = project.name
    version = rootProject.findProperty("version") as String? ?: "1.0.0-SNAPSHOT"

    foliaSupported = false
    apiVersion = "1.21"
    authors = listOf("SLNE Development", "Ammo")
    main = "dev.slne.augmented.common.base.plugin.bukkit.AugmentedBukkitPlugin"

    serverDependencies {
        register("CommandAPI") {
            load = PaperPluginDescription.RelativeLoadOrder.BEFORE
            required = true
        }
        register("augmented-shop-bukkit") {
            load = PaperPluginDescription.RelativeLoadOrder.AFTER
            required = false
            joinClasspath = true
        }
    }
}