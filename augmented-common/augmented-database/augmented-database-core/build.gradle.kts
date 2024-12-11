plugins {
    id("common-conventions")
    id("shadow-conventions")
}

dependencies {
    api(libs.hibernate)
    api(libs.hibernate.hikari)
    api(libs.jakarta.transactions)
    runtimeOnly(libs.mariadb)

    api(project(":augmented-common:augmented-base:augmented-base-core"))
}