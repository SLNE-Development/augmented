plugins {
    `common-conventions`
    `shadow-conventions`
    `bukkit-conventions`
}

dependencies {
    api(project(":augmented-shop:augmented-shop-core"))
    api(project(":augmented-common:augmented-database:augmented-database-bukkit"))

//    compileOnlyApi(libs.packetuxui)
}

paper {
    main = "dev.slne.augmented.shop.bukkit.AugmentedShopPlugin"

    serverDependencies {
//        register("packetuxui-bukkit") {
//            load = PaperPluginDescription.RelativeLoadOrder.BEFORE
//            required = true
//            joinClasspath = true
//        }
    }
}