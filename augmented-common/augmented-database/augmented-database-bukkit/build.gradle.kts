plugins {
    id("common-conventions")
    id("shadow-conventions")
}

dependencies {
    api(project(":augmented-common:augmented-database:augmented-database-core"))
    api(project(":augmented-common:augmented-base:augmented-base-bukkit"))
}