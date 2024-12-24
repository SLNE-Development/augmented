plugins {
    `common-conventions`
    `shadow-conventions`

    kotlin("jvm")
    kotlin("kapt")
}

dependencies {
    compileOnlyApi(libs.configurate.hocon)
    compileOnlyApi(libs.configurate.kotlin)
    compileOnlyApi(libs.caffeine)
    compileOnlyApi(libs.caffeine.coroutines)
    compileOnlyApi(libs.fastutil)
    compileOnlyApi(libs.coroutines)
    compileOnlyApi(libs.adventure.api)
    compileOnlyApi(libs.adventure.gson)
    compileOnlyApi(libs.adventure.logger)
    compileOnlyApi(libs.autoservice)
}