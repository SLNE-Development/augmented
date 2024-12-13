import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    id("com.gradleup.shadow")
}

tasks.withType<ShadowJar> {
    archiveClassifier.set("shadow")
    archiveFileName.set("${project.name}-shadow.jar")

    mergeServiceFiles()

    isZip64 = true
}