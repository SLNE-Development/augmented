plugins {
    `kotlin-dsl`
}

repositories {
    mavenCentral()
    gradlePluginPortal()
}

dependencies {
    implementation(libs.kotlin.jvm)
    implementation(libs.shadow.jar)
    implementation(libs.repo.auth)

    implementation(libs.run.paper)
    implementation(libs.run.velocity)
    implementation(libs.plugin.yml)
}
