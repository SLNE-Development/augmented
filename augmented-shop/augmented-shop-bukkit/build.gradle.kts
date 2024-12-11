plugins {
    `common-conventions`
    `shadow-conventions`
    `bukkit-conventions`
    `bukkit-server-conventions`
}

dependencies {
    api(project(":augmented-shop:augmented-shop-core"))
    api(project(":augmented-common:augmented-database:augmented-database-bukkit"))
    api(project(":augmented-common:augmented-gui"))
}

bukkit {
    main = "dev.slne.augmented.shop.bukkit.AugmentedShopPlugin"
}