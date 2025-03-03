plugins {
    `kotlin-dsl`
}

repositories {
    mavenCentral()
    gradlePluginPortal()
}

dependencies {
    implementation(files(libs.javaClass.superclass.protectionDomain.codeSource.location))
    
    implementation(libs.kotlin.jvm)
    implementation(libs.kotlin.serialization)
//    implementation(libs.shadow.jar)
    implementation(libs.repo.auth)

    implementation(libs.run.paper)
    implementation(libs.run.velocity)
    implementation(libs.plugin.yml)
}
