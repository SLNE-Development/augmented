plugins {
    `common-conventions`
    `shadow-conventions`
}

dependencies {
    compileOnlyApi(project(":augmented-common:augmented-base:augmented-base-core"))
    compileOnlyApi(project(":augmented-common:augmented-database:augmented-database-core"))
}
