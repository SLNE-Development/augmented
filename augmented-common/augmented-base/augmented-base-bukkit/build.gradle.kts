plugins {
    `common-conventions`
    `shadow-conventions`
    `bukkit-conventions`
}

dependencies {
    api(project(":augmented-common:augmented-base:augmented-base-core"))

    api(libs.adventure.platform.bukkit)
}