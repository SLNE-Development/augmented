plugins {
    `common-conventions`
    `shadow-conventions`

    kotlin("jvm")
    kotlin("kapt")
}

dependencies {
    implementation(libs.kotlin.jvm)

    api(libs.configurate.hocon)
    api(libs.configurate.kotlin)
    api(libs.caffeine)
    api(libs.caffeine.coroutines)

    compileOnlyApi(libs.adventure.api)
    compileOnlyApi(libs.adventure.gson)
    compileOnlyApi(libs.adventure.logger)

    api(libs.fastutil)
    api(libs.coroutines)
    compileOnlyApi(libs.autoservice)
}