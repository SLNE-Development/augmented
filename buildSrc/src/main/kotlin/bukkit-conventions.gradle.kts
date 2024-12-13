plugins {
    `java-library`
}

val libs: VersionCatalog = the<VersionCatalogsExtension>().named("libs")

dependencies {
    compileOnlyApi(libs.findLibrary("commandapi-bukkit").orElseThrow())
    compileOnlyApi(libs.findLibrary("commandapi-bukkit-kotlin").orElseThrow())
    compileOnlyApi(libs.findLibrary("paper-api").orElseThrow())
    compileOnlyApi(libs.findLibrary("mccoroutine-folia").orElseThrow())
    compileOnlyApi(libs.findLibrary("mccoroutine-folia-core").orElseThrow())
}