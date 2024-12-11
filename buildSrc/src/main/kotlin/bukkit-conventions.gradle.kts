plugins {
    `java-library`
}

val libs: VersionCatalog = the<VersionCatalogsExtension>().named("libs")

dependencies {
    compileOnlyApi(libs.findLibrary("commandapi-bukkit").orElseThrow())
    compileOnlyApi(libs.findLibrary("commandapi-bukkit-kotlin").orElseThrow())
    compileOnlyApi(libs.findLibrary("spigot-api").orElseThrow())
    api(libs.findLibrary("mccoroutine-folia").orElseThrow())
    api(libs.findLibrary("mccoroutine-folia-core").orElseThrow())
}