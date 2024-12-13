plugins {
    `java-library`
    `maven-publish`

    kotlin("jvm")
    kotlin("kapt")
}

group = "dev.slne"
version = findProperty("version") as String? ?: "1.0.0-SNAPSHOT"

repositories {
    mavenCentral()

    maven("https://repo.codemc.org/repository/maven-public/")
    maven("https://s01.oss.sonatype.org/content/repositories/snapshots")
    maven("https://repo.papermc.io/repository/maven-public/") { name = "papermc" }
}

val libs: VersionCatalog = the<VersionCatalogsExtension>().named("libs")
dependencies {
    kapt(libs.findLibrary("autoservice").orElseThrow())
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}