plugins {
    `common-conventions`
    `shadow-conventions`
}

dependencies {
    api(project(":augmented-common:augmented-base:augmented-base-core"))
    api(project(":augmented-common:augmented-database:augmented-database-core"))
}
