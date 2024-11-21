plugins {
    id("dev.slne.common")
    id("dev.slne.shadow")
}

dependencies {
    api(libs.hibernate)
    api(libs.hibernate.hikari)
    api(libs.jakarta.transactions)
    runtimeOnly(libs.mariadb)

    api(project(":augmented-common:augmented-base:augmented-base-core"))
}