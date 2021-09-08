application {
    mainClass.set("io.ktor.server.netty.EngineMain")
}

plugins {
    application
    kotlin("jvm")
}

dependencies {
    implementation(Lib.Kotlin.kotlinStdLib)
    implementation(Lib.Log.logback)
    implementation(Lib.Ktor.core)
    implementation(Lib.Ktor.netty)
    implementation(Lib.Ktor.gson)
    implementation(Lib.Ktor.authJwt)

    implementation(Lib.Exposed.core)
    implementation(Lib.Exposed.dao)
    implementation(Lib.Exposed.jdbc)

    implementation(Lib.Database.hikariCp)
    implementation(Lib.Database.flywayCore)
    implementation(Lib.Database.ktorFlyway)
    implementation(Lib.Database.postgresql)

    implementation(Lib.Koin.koinLib)

    implementation(Lib.Other.bcrypt)

    testImplementation(Lib.Ktor.tests)
}
