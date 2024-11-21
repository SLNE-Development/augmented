plugins {
    `java-library`
    `maven-publish`

    id("org.jetbrains.kotlin.jvm")
}

group = "dev.slne"
version = findProperty("version") as String? ?: "1.0.0-SNAPSHOT"

repositories {
    mavenCentral()

    maven("https://repo.codemc.org/repository/maven-public/")
    maven("https://s01.oss.sonatype.org/content/repositories/snapshots")
    maven {
        name = "spigot-repo"
        url = uri("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    }
}

val libs: VersionCatalog = the<VersionCatalogsExtension>().named("libs")
dependencies {
    api(libs.findLibrary("fastutil").orElseThrow())
    api(libs.findLibrary("coroutines").orElseThrow())
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}