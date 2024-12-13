plugins {
    `common-conventions`
    `shadow-plugin-conventions`
    `bukkit-conventions`
    `bukkit-server-conventions`
}

dependencies {
    api(project(":augmented-shop:augmented-shop-core"))
    compileOnlyApi(project(":augmented-common:augmented-database:augmented-database-bukkit"))
    compileOnlyApi(project(":augmented-common:augmented-gui"))
}

paper {
    main = "dev.slne.augmented.shop.bukkit.AugmentedShopPlugin"
}