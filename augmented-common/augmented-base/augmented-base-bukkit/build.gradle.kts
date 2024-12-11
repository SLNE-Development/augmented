plugins {
    id("common-conventions")
    id("shadow-conventions")
    id("bukkit-conventions")
}

dependencies {
    api(project(":augmented-common:augmented-base:augmented-base-core"))

    api(libs.adventure.platform.bukkit)
}
