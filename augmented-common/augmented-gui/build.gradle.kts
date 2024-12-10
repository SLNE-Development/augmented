plugins {
    id("dev.slne.common")
    id("dev.slne.shadow")
    id("dev.slne.bukkit")
}

dependencies {
    api(project(":augmented-common:augmented-base:augmented-base-bukkit"))
}

