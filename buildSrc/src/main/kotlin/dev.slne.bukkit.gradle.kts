plugins {
    `java-library`
}

val libs: VersionCatalog = the<VersionCatalogsExtension>().named("libs")
val spigotApi: ModuleDependency = libs.findLibrary("spigot-api").orElseThrow().get()
dependencies {
    compileOnlyApi(libs.findLibrary("commandapi-bukkit").orElseThrow())
    compileOnlyApi(libs.findLibrary("commandapi-bukkit-kotlin").orElseThrow())
    compileOnlyApi(spigotApi)
    api(libs.findLibrary("mccoroutine-folia").orElseThrow())
    api(libs.findLibrary("mccoroutine-folia-core").orElseThrow())
}