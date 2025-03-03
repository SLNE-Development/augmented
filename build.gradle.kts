import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.gradle.kotlin.dsl.withType

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
            "org.jetbrains" to "$group.libs.jetbrains"
        )

        relocations.forEach { (from, to) ->
            relocate(from, to)
        }
    }
}