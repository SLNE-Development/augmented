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

    paperLibrary(libs.kaml)
    paperLibrary(libs.caffeine)
    paperLibrary(libs.caffeine.coroutines)
    paperLibrary(libs.fastutil)
    paperLibrary(libs.coroutines)
    paperLibrary(libs.hibernate)
    paperLibrary(libs.hibernate.hikari)
    paperLibrary(libs.jakarta.transactions)
    paperLibrary(libs.mariadb)
}

paper {
    name = project.name
    version = rootProject.findProperty("version") as String? ?: "undefined"

    foliaSupported = true
    apiVersion = "1.21"
    authors = listOf("SLNE Development", "Ammo")
    main = "dev.slne.augmented.common.base.plugin.bukkit.AugmentedBukkitPlugin"

    loader = "dev.slne.augmented.common.base.plugin.bukkit.PluginLibrariesLoader"
    generateLibrariesJson = true

    hasOpenClassloader = true

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