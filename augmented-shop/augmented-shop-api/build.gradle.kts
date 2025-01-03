plugins {
    `common-conventions`
    `shadow-conventions`
}

dependencies {
    compileOnlyApi(project(":augmented-common:augmented-database:augmented-database-core"))
}
