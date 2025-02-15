plugins {
    `java-library`
    `maven-publish`

    kotlin("jvm")
    kotlin("kapt")
    kotlin("plugin.serialization")
}

group = "dev.slne"
version = findProperty("version") as String? ?: "1.0.0-SNAPSHOT"

tasks.withType<AbstractArchiveTask> {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}

repositories {
    mavenCentral()
    maven("https://repo.slne.dev/repository/maven-public/") { name = "maven-public" }
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