plugins {
    id("dev.slne.common")
    id("dev.slne.shadow")
}

dependencies {
    api(project(":augmented-common:augmented-database:augmented-database-core"))
    api(project(":augmented-common:augmented-base:augmented-base-bukkit"))
}