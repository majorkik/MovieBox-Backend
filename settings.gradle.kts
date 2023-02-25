pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)

    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        maven("https://kotlin.bintray.com/ktor")
        maven("https://kotlin.bintray.com/kotlinx")
        maven("https://jitpack.io")
    }
}

rootProject.name = "moviebox"

include(":app")
