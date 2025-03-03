plugins {
    `common-conventions`

    kotlin("jvm")
    kotlin("kapt")
}

dependencies {
    compileOnlyApi(libs.kaml)
    compileOnlyApi(libs.caffeine)
    compileOnlyApi(libs.caffeine.coroutines)
    compileOnlyApi(libs.fastutil)
    compileOnlyApi(libs.coroutines)
    compileOnlyApi(libs.adventure.api)
    compileOnlyApi(libs.adventure.gson)
    compileOnlyApi(libs.adventure.logger)
    compileOnlyApi(libs.autoservice)
    compileOnlyApi(libs.advkt)
}