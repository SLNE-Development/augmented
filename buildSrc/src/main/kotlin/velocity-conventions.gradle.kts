plugins {
    `java-library`

    id("xyz.jpenilla.run-velocity")
}

val libs: VersionCatalog = the<VersionCatalogsExtension>().named("libs")
val velocityApi: Provider<MinimalExternalModuleDependency> =
    libs.findLibrary("velocity-api").orElseThrow()
dependencies {
    compileOnlyApi(velocityApi)
    annotationProcessor(velocityApi)

    api(libs.findLibrary("commandapi-velocity").orElseThrow())
    api(libs.findLibrary("commandapi-velocity-kotlin").orElseThrow())
    api(libs.findLibrary("mccoroutine-velocity").orElseThrow())
    api(libs.findLibrary("mccoroutine-velocity-core").orElseThrow())
}

tasks.runVelocity {
    velocityVersion(velocityApi.get().version!!)

    downloadPlugins {
        url("https://s01.oss.sonatype.org/content/repositories/snapshots/dev/jorel/commandapi-velocity-plugin/9.6.0-SNAPSHOT/commandapi-velocity-plugin-9.6.0-20241023.203559-32.jar")
    }
}