object Version {
    // Kotlin
    const val kotlin = "1.5.21"

    // Versions
    const val gradleVersions = "0.39.0"

    // Static analysis tools
    const val spotless = "5.14.2"
    const val ktlintJLLeitschuh = "10.1.0"
    const val ktlint = "0.42.0"
    const val detekt = "1.17.1"
    const val koin = "2.2.2"
}

object Plugin {
    // Updates versions
    const val gradleVersions = "com.github.ben-manes.versions"

    // Static analysis plugins
    const val detekt = "io.gitlab.arturbosch.detekt"
    const val ktlint = "org.jlleitschuh.gradle.ktlint"
    const val spotless = "com.diffplug.spotless"

    // Koin
    const val koin = "koin"
}

object Lib {
    object Kotlin {
        const val kotlinStdLib = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${Version.kotlin}"
    }

    object Ktor {
        private const val version = "1.6.2"

        const val netty = "io.ktor:ktor-server-netty:$version"
        const val core = "io.ktor:ktor-server-core:$version"
        const val gson = "io.ktor:ktor-gson:$version"
        const val tests = "io.ktor:ktor-server-tests:$version"
        const val authJwt = "io.ktor:ktor-auth-jwt:$version"
    }

    object Exposed {
        private const val version = "0.32.1"

        const val core = "org.jetbrains.exposed:exposed-core:$version"
        const val dao = "org.jetbrains.exposed:exposed-dao:$version"
        const val jdbc = "org.jetbrains.exposed:exposed-jdbc:$version"
    }

    object Database {
        const val hikariCp = "com.zaxxer:HikariCP:5.0.0"
        const val ktorFlyway = "com.viartemev:ktor-flyway-feature:1.3.0"
        const val flywayCore = "org.flywaydb:flyway-core:7.12.1"
        const val postgresql = "org.postgresql:postgresql:42.2.23"
    }

    object Koin {
        const val koinLib = "org.koin:koin-ktor:${Version.koin}"
    }

    object Log {
        const val logback = "ch.qos.logback:logback-classic:1.2.5"
    }
}
