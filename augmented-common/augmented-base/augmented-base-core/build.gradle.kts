plugins {
    `common-conventions`
    `shadow-conventions`
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