plugins {
    `common-conventions`
    `shadow-conventions`
    `bukkit-conventions`
    `bukkit-server-conventions`
}

dependencies {
    api(project(":augmented-shop:augmented-shop-core"))
    compileOnlyApi(project(":augmented-common:augmented-database:augmented-database-bukkit"))
    compileOnlyApi(libs.packetuxui)
}

paper {
    main = "dev.slne.augmented.shop.bukkit.AugmentedShopPlugin"
}

tasks.runServer {
    downloadPlugins {
        url("https://repo.slne.dev/repository/maven-unsafe/net/craftoriya/packetuxui-bukkit/1.0.1-SNAPSHOT/packetuxui-bukkit-1.0.1-20241224.044819-1-all.jar")
    }
}