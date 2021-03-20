plugins {
    application
    kotlin("jvm") version Versions.kotlin
}

group = ProjectConfig.group
version = ProjectConfig.version

application {
    mainClass.set("io.ktor.server.netty.EngineMain")
}

repositories {
    mavenLocal()
    jcenter()
    maven { setUrl("https://kotlin.bintray.com/ktor") }
    maven { setUrl("https://kotlin.bintray.com/kotlinx") }
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
