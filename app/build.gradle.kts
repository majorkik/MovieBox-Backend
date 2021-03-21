application {
    mainClass.set("io.ktor.server.netty.EngineMain")
}

plugins {
    application
    kotlin("jvm")
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
