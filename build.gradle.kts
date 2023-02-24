import org.jlleitschuh.gradle.ktlint.reporter.ReporterType.CHECKSTYLE
import org.jlleitschuh.gradle.ktlint.reporter.ReporterType.PLAIN

plugins {
    base
    kotlin("jvm") version Version.kotlin
    id(Plugin.ktlint) version Version.ktlintJLLeitschuh
    id(Plugin.detekt) version Version.detekt
    id(Plugin.spotless) version Version.spotless
    id(Plugin.gradleVersions) version Version.gradleVersions
}

allprojects {
    group = ProjectConfig.group
    version = ProjectConfig.version

    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        maven("https://kotlin.bintray.com/ktor")
        maven("https://kotlin.bintray.com/kotlinx")
        maven("https://jitpack.io")
    }

    apply {
        plugin("kotlin")

        plugin(Plugin.ktlint)
        plugin(Plugin.spotless)
        plugin(Plugin.detekt)
    }

    // Ktlint configuration for sub-projects
    ktlint {
        // Version of ktlint cmd tool (Ktlint Gradle plugin is just a wrapper for this tool)
        version.set(Version.ktlint)
        debug.set(true)
        verbose.set(true)
        android.set(true)
        outputToConsole.set(true)
        outputColorName.set("BLUE")
        ignoreFailures.set(true)
        enableExperimentalRules.set(true)

        // Uncomment below line and run .\gradlew ktlintCheck to see check ktlint experimental rules
        // enableExperimentalRules.set(true)

        reporters {
            reporter(PLAIN)
            reporter(CHECKSTYLE)
        }

        kotlinScriptAdditionalPaths {
            include(fileTree("scripts/"))
        }
        filter {
            exclude("**/generated/**")
            include("**/kotlin/**")
        }
    }

    spotless {
        kotlin {
            target("**/*.kt")
            // ktlint()
            trimTrailingWhitespace()
            indentWithSpaces()
            endWithNewline()
        }
        format("misc") {
            target("**/*.gradle", "**/*.md", "**/.gitignore")
            indentWithSpaces()
            trimTrailingWhitespace()
            endWithNewline()
        }

        kotlinGradle {
            target("*.gradle.kts")
            ktlint()
        }

        format("xml") {
            target("**/*.xml")
            indentWithSpaces()
            trimTrailingWhitespace()
            endWithNewline()
        }
    }

    detekt {
        config = files("$rootDir/detekt.yml")

        parallel = true
        // By default detekt does not check test source set and gradle specific files, so hey have to be added manually
        input = files(
            "$rootDir/buildSrc",
            "$rootDir/build.gradle.kts",
            "$rootDir/settings.gradle.kts",
            "src/main/kotlin",
            "src/test/kotlin"
        )

        autoCorrect = true
    }
}

tasks.create("stage") {
    dependsOn("installDist")
}
