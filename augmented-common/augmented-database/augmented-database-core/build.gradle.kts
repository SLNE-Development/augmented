plugins {
    `common-conventions`
    
}

dependencies {
    api(project(":augmented-common:augmented-base:augmented-base-core"))

    compileOnlyApi(libs.exposed.core)
    compileOnlyApi(libs.exposed.dao)
    compileOnlyApi(libs.exposed.jdbc)
    compileOnlyApi(libs.hikari)
}