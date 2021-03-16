object Versions {
    const val kotlin = "1.4.30"
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
    }

    object Log {
        const val logback = "ch.qos.logback:logback-classic:1.2.1"
    }
}