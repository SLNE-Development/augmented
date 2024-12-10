plugins {
    id("dev.slne.common")
    id("dev.slne.shadow")
    id("dev.slne.bukkit")
    id("dev.slne.bukkit-server")
}

dependencies {
    api(project(":augmented-shop:augmented-shop-core"))
    api(project(":augmented-common:augmented-database:augmented-database-bukkit"))
    api(project(":augmented-common:augmented-gui"))
}

bukkit {
    main = "dev.slne.augmented.shop.bukkit.AugmentedShopPlugin"
}