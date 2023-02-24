object Version {
    // Kotlin
    const val kotlin = "1.8.10"

    // Versions
    const val gradleVersions = "0.46.0"

    // Static analysis tools
    const val spotless = "6.15.0"
    const val ktlintJLLeitschuh = "11.2.0"
    const val ktlint = "0.48.2"
    const val detekt = "1.22.0"
    const val koin = "3.3.1"
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
        private const val version = "2.2.3"

        const val netty = "io.ktor:ktor-server-netty-jvm:$version"
        const val core = "io.ktor:ktor-server-core-jvm:$version"
        const val statusPages = "io.ktor:ktor-server-status-pages-jvm:$version"
        const val headersJvm = "io.ktor:ktor-server-default-headers-jvm:$version"
        const val contentNegotiation = "io.ktor:ktor-server-content-negotiation:$version"
        const val serialization = "io.ktor:ktor-serialization-kotlinx-json:$version"
        const val cors = "io.ktor:ktor-server-cors:$version"
        const val logging = "io.ktor:ktor-server-call-logging:$version"
        const val serverSessions = "io.ktor:ktor-server-sessions:$version"
        const val auth = "io.ktor:ktor-server-auth:$version"
        const val authJwt = "io.ktor:ktor-server-auth-jwt:$version"
    }

    object Exposed {
        private const val version = "0.41.1"

        const val core = "org.jetbrains.exposed:exposed-core:$version"
        const val dao = "org.jetbrains.exposed:exposed-dao:$version"
        const val jdbc = "org.jetbrains.exposed:exposed-jdbc:$version"
    }

    object Database {
        const val hikariCp = "com.zaxxer:HikariCP:5.0.1"
        const val ktorFlyway = "com.viartemev:ktor-flyway-feature:1.3.0"
        const val flywayCore = "org.flywaydb:flyway-core:9.15.1"
        const val postgresql = "org.postgresql:postgresql:42.5.4"
    }

    object Koin {
        const val koinLib = "io.insert-koin:koin-ktor:${Version.koin}"
    }

    object Log {
        const val logback = "ch.qos.logback:logback-classic:1.4.5"
    }

    object Other {
        const val bcrypt = "at.favre.lib:bcrypt:0.10.2"
    }
}
