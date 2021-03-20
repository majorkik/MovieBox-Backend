plugins {
    application
    kotlin("jvm") version Versions.kotlin
    id(Plugins.ktlint) version Versions.ktlint
    id(Plugins.gradleVersions) version Versions.gradleVersions
}

group = ProjectConfig.group
version = ProjectConfig.version

allprojects {
    repositories {
        mavenLocal()
        jcenter()
        maven { setUrl("https://kotlin.bintray.com/ktor") }
        maven { setUrl("https://kotlin.bintray.com/kotlinx") }
    }

    // We want to apply ktlint at all project level because it also checks build gradle files
    apply(plugin = Plugins.ktlint)

    // Ktlint configuration for sub-projects
    ktlint {
        version.set("0.40.0")
        verbose.set(true)
        android.set(true)

        reporters {
            reporter(org.jlleitschuh.gradle.ktlint.reporter.ReporterType.CHECKSTYLE)
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
