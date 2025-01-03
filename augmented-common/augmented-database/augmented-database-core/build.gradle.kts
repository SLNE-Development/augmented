plugins {
    `common-conventions`
    `shadow-conventions`
}

dependencies {
    compileOnlyApi(libs.hibernate)
    compileOnlyApi(libs.hibernate.hikari)
    compileOnlyApi(libs.jakarta.transactions)

    api(project(":augmented-common:augmented-base:augmented-base-core"))
}