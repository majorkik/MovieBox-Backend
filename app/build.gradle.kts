plugins {
    application
    alias(libs.plugins.jvm)
}

application {
    mainClass.set("io.ktor.server.netty.EngineMain")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

dependencies {
    implementation(libs.bundles.kotlin)
    implementation(libs.bundles.ktor)
    implementation(libs.bundles.database)

    implementation(libs.bcrypt)
    implementation(libs.koin.ktor)
}
