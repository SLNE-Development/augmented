plugins {
    `common-conventions`
    `shadow-conventions`
    `bukkit-conventions`
    `bukkit-server-conventions`
}

dependencies {
    api(project(":augmented-shop:augmented-shop-core"))
    compileOnlyApi(project(":augmented-common:augmented-database:augmented-database-bukkit"))
    compileOnlyApi(project(":augmented-common:augmented-gui"))
}

bukkit {
    main = "dev.slne.augmented.shop.bukkit.AugmentedShopPlugin"
}

paper {
    main = "dev.slne.augmented.shop.bukkit.AugmentedShopPlugin"
}

tasks.runServer {
//    pluginJars.from(project(":augmented-shop:augmented-shop-core").tasks.shadowJar)
}