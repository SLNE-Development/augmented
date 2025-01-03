plugins {
    `java-library`
    `maven-publish`

    kotlin("jvm")
    kotlin("kapt")
    kotlin("plugin.serialization")

    id("org.hibernate.build.maven-repo-auth")
}

group = "dev.slne"
version = findProperty("version") as String? ?: "1.0.0-SNAPSHOT"

repositories {
    mavenCentral()

    maven("https://repo.codemc.org/repository/maven-public/")
    maven("https://s01.oss.sonatype.org/content/repositories/snapshots")
    maven("https://repo.papermc.io/repository/maven-public/") { name = "papermc" }
    maven("https://repo.slne.dev/repository/maven-snapshots/") { name = "maven-snapshots" }
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