plugins {
    `common-conventions`
    `shadow-conventions`
}

dependencies {
    compileOnlyApi(libs.exposed.core)
    compileOnlyApi(libs.exposed.dao)
    compileOnlyApi(libs.exposed.jdbc)
    compileOnlyApi(libs.hikari)

    api(project(":augmented-common:augmented-base:augmented-base-core"))
}