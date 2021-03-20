object Versions {
    // Kotlin
    const val kotlin = "1.4.31"

    // Versions
    const val gradleVersions = "0.38.0"

    // Static analysis tools
    const val spotless = "5.11.0"
    const val ktlintJLLeitschuh = "10.0.0"
    const val ktlint = "0.41.0"
    const val detekt = "1.16.0"
    const val koin = "2.2.2"
}

object Plugins {
    // Updates versions
    const val gradleVersions = "com.github.ben-manes.versions"

    // Static analysis plugins
    const val detekt = "io.gitlab.arturbosch.detekt"
    const val ktlint = "org.jlleitschuh.gradle.ktlint"
    const val spotless = "com.diffplug.spotless"

    // Koin
    const val koin = "koin"
}

object Libs {
    object Kotlin {
        const val kotlinStdLib = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${Versions.kotlin}"
    }

    object Ktor {
        private const val version = "1.5.2"

        const val netty = "io.ktor:ktor-server-netty:$version"
        const val core = "io.ktor:ktor-server-core:$version"
        const val gson = "io.ktor:ktor-gson:$version"
        const val tests = "io.ktor:ktor-server-tests:$version"
        const val authJwt = "io.ktor:ktor-auth-jwt:$version"
    }

    object Exposed {
        private const val version = "0.29.1"

        const val core = "org.jetbrains.exposed:exposed-core:$version"
        const val dao = "org.jetbrains.exposed:exposed-dao:$version"
        const val jdbc = "org.jetbrains.exposed:exposed-jdbc:$version"
    }

    object Database {
        const val hikariCp = "com.zaxxer:HikariCP:4.0.3"
        const val ktorFlyway = "com.viartemev:ktor-flyway-feature:1.2.2"
        const val flywayCore = "org.flywaydb:flyway-core:7.7.0"
        const val postgresql = "org.postgresql:postgresql:42.2.19"
    }

    object Koin {
        const val koin = "org.koin:koin-ktor:${Versions.koin}"
    }

    object Log {
        const val logback = "ch.qos.logback:logback-classic:1.2.3"
    }
}
