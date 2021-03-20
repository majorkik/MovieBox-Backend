import org.jlleitschuh.gradle.ktlint.reporter.ReporterType.CHECKSTYLE
import org.jlleitschuh.gradle.ktlint.reporter.ReporterType.PLAIN

plugins {
    application
    kotlin("jvm") version Versions.kotlin
    id(Plugins.ktlint) version Versions.ktlintJLLeitschuh
    id(Plugins.detekt) version Versions.detekt
    id(Plugins.koin) version Versions.koin
    id(Plugins.spotless) version Versions.spotless
    id(Plugins.gradleVersions) version Versions.gradleVersions
}

group = ProjectConfig.group
version = ProjectConfig.version

allprojects {
    repositories {
        mavenLocal()
        jcenter()
        maven("https://kotlin.bintray.com/ktor")
        maven("https://kotlin.bintray.com/kotlinx")
        maven("https://jitpack.io")
    }

    apply {
        plugin(Plugins.koin)

        plugin(Plugins.ktlint)
        plugin(Plugins.spotless)
        plugin(Plugins.detekt)
    }

    // Ktlint configuration for sub-projects
    ktlint {
        // Version of ktlint cmd tool (Ktlint Gradle plugin is just a wrapper for this tool)
        version.set(Versions.ktlint)
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
        java {
            target("**/*.java")
            googleJavaFormat().aosp()
            removeUnusedImports()
            trimTrailingWhitespace()
            indentWithSpaces()
            endWithNewline()
        }
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
}

application {
    mainClass.set("io.ktor.server.netty.EngineMain")
}

dependencies {
    implementation(Libs.Kotlin.kotlinStdLib)
    implementation(Libs.Log.logback)
    implementation(Libs.Ktor.core)
    implementation(Libs.Ktor.netty)
    implementation(Libs.Ktor.gson)
    implementation(Libs.Ktor.authJwt)

    implementation(Libs.Exposed.core)
    implementation(Libs.Exposed.dao)
    implementation(Libs.Exposed.jdbc)

    implementation(Libs.Database.hikariCp)
    implementation(Libs.Database.flywayCore)
    implementation(Libs.Database.ktorFlyway)
    implementation(Libs.Database.postgresql)

    implementation(Libs.Koin.koin)

    testImplementation(Libs.Ktor.tests)
}

tasks.create("stage") {
    dependsOn("installDist")
}

kotlin.sourceSets["main"].kotlin.srcDirs("src")
kotlin.sourceSets["test"].kotlin.srcDirs("test")

sourceSets["main"].resources.srcDirs("resources")
sourceSets["test"].resources.srcDirs("testresources")

tasks {
    // Gradle versions plugin configuration
    "dependencyUpdates"(com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask::class) {
        resolutionStrategy {
            componentSelection {
                all {
                    // Do not show pre-release version of library in generated dependency report
                    val rejected = listOf("alpha", "beta", "rc", "cr", "m", "preview")
                        .map { qualifier -> Regex("(?i).*[.-]$qualifier[.\\d-]*") }
                        .any { it.matches(candidate.version) }
                    if (rejected) {
                        reject("Release candidate")
                    }

                    // kAndroid newest version is 0.8.8 (jcenter), however maven repository contains version 0.8.7 and
                    // plugin fails to recognize it correctly
                    if (candidate.group == "com.pawegio.kandroid") {
                        reject("version ${candidate.version} is broken for ${candidate.group}'")
                    }
                }
            }
        }
    }
}
