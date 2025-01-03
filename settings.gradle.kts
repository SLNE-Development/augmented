plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}

rootProject.name = "augmented"

val projects: List<Pair<String, String>> = listOf(
    // region Common
    // region Common Base
    "augmented-common:augmented-base:augmented-base-core" to "AugmentedBaseCore",
    "augmented-common:augmented-base:augmented-base-bukkit" to "AugmentedBaseBukkit",
    // endregion

    // region Common Database
    "augmented-common:augmented-database:augmented-database-core" to "AugmentedDatabaseCore",
    "augmented-common:augmented-database:augmented-database-bukkit" to "AugmentedDatabaseBukkit",
    // endregion

    // region Common Plugins
    "augmented-common:augmented-base:augmented-base-plugins:augmented-base-plugin-bukkit" to "AugmentedBasePluginBukkit",
    "augmented-common:augmented-base:augmented-base-plugins:augmented-base-plugin-velocity" to "AugmentedBasePluginVelocity",
    // endregion
    // endregion

    // region Shop
    "augmented-shop:augmented-shop-api" to "AugmentedShopApi",
    "augmented-shop:augmented-shop-core" to "AugmentedShopCore",
    "augmented-shop:augmented-shop-bukkit" to "AugmentedShopBukkit",
    // endregion

    // region Cabin
    "augmented-cabin:augmented-cabin-api" to "AugmentedCabinApi",
    "augmented-cabin:augmented-cabin-plugins:augmented-cabin-plugin-bukkit" to "AugmentedCabinPluginBukkit",

    // region Cabin Extensions
    "augmented-cabin:augmented-cabin-extensions:augmented-cabin-extension-yml" to "AugmentedCabinExtensionYml",
    // endregion
    // endregion
)

projects.forEach { (path, _) ->
    include(path)
}
