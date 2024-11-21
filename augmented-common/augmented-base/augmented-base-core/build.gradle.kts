plugins {
    id("dev.slne.common")
    id("dev.slne.shadow")
}

dependencies {
    api(libs.configurate.hocon)
    api(libs.configurate.kotlin)
    api(libs.caffeine)
    api(libs.caffeine.coroutines)

    api(libs.adventure.api)
    api(libs.adventure.gson)
    api(libs.adventure.logger)
}