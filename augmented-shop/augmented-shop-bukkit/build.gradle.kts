import net.minecrell.pluginyml.paper.PaperPluginDescription

plugins {
    `common-conventions`
    
    `bukkit-conventions`
}

dependencies {
    api(project(":augmented-shop:augmented-shop-core"))
    api(project(":augmented-common:augmented-database:augmented-database-bukkit"))

    compileOnlyApi(libs.surf.gui)
}

paper {
    main = "dev.slne.augmented.shop.bukkit.AugmentedShopPlugin"

    serverDependencies {
        register("surf-gui-bukkit") {
            load = PaperPluginDescription.RelativeLoadOrder.BEFORE
            required = true
        }
    }
}

tasks.runServer {
    downloadPlugins {
        github(
            "SLNE-Development",
            "surf-gui",
            "v2.0.1-SNAPSHOT",
            "surf-gui-bukkit-2.0.1-SNAPSHOT-all.jar"
        )
    }
}