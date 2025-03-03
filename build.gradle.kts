import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    id("com.gradleup.shadow") version libs.versions.shadow.jar.get()
}

allprojects {
    apply(plugin = "com.gradleup.shadow")

    tasks.withType<ShadowJar> {
        archiveClassifier.set("")
        mergeServiceFiles()

        exclude("kotlin/**")
        val group = "dev.slne.augmented"
        val relocations = mapOf(
            "com.github.shynixn.mccoroutine" to "$group.libs.mccoroutine",
            "org.intellij" to "$group.libs.intellij",
            "org.jetbrains.annotations" to "$group.libs.jetbrains.annotations"
        )

        relocations.forEach { (from, to) ->
            relocate(from, to)
        }
    }
}