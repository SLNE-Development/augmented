plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}

rootProject.name = "augmented"

// region Common
// region Common Base
include("augmented-common:augmented-base:augmented-base-core")
include("augmented-common:augmented-base:augmented-base-bukkit")
// endregion

// region Common Database
include("augmented-common:augmented-database:augmented-database-core")
include("augmented-common:augmented-database:augmented-database-bukkit")
// endregion

// region Common GUI
include("augmented-common:augmented-gui")
//endregion
// endregion

// region Shop
include("augmented-shop:augmented-shop-api")
include("augmented-shop:augmented-shop-core")
include("augmented-shop:augmented-shop-bukkit")
// endregion
