plugins {
    `common-conventions`
    `shadow-conventions`
}

dependencies {
    compileOnlyApi(project(":augmented-cabin:augmented-cabin-api"))
}
