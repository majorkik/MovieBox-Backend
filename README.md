# MovieBox Backend - Ktor

[![Kotlin Version][badge-kotlin]](https://kotlinlang.org)
[![Ktor version][badge-ktor]](https://ktor.io)
[![Gradle][badge-gradle]](https://gradle.org)
[![CodeFactor][badge-codefactor]](https://www.codefactor.io/repository/github/majorkik/moviebox-backend)

#### Status 🚧 [Work in progress] 🚧

## :rocket: Project characteristics and tech-stack

- [**100% Kotlin**](https://kotlinlang.org/) + [**Coroutines**](https://kotlinlang.org/docs/reference/coroutines-overview.html) - perform background operations
- [**Ktor**](https://github.com/ktorio/ktor) - Framework for quickly creating connected applications in Kotlin with minimal
  effort
- [**Logback**](https://ktor.io/docs/logging.html) - Logback is intended as a successor to the popular log4j project
- [**Exposed**](https://github.com/JetBrains/Exposed) - Kotlin SQL Framework
- [**Hikari**](https://github.com/brettwooldridge/HikariCP) - Solid, high-performance, JDBC connection pool at last
- [**Flyway**](https://github.com/flyway/flyway) - Database Migrations Made Easy
- [**PostgreSQL**](https://www.postgresql.org/)

## Getting Started

```shell
$ gradle run # Application start and server start
```

## License

```
MIT License

Copyright (c) 2021 Rodion Belovitskiy

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
associated documentation files (the "Software"), to deal in the Software without restriction, including
without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to
the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial
portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT
LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN
NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
WHETHER IN AN ACTION OF  TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE
SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
```

[badge-kotlin]: https://img.shields.io/badge/Kotlin-1.8.10-brightgreen?style=flat-square&logo=appveyor "Kotlin Version"

[badge-ktor]: https://img.shields.io/badge/Ktor-1.6.2-red?style=flat-square&logo=appveyor "Ktor"

[badge-gradle]: https://img.shields.io/badge/Gradle-7.1.1-blue?style=flat-square&logo=appveyor "Gradle"

[badge-codefactor]: https://www.codefactor.io/repository/github/majorkik/moviebox-backend/badge?style=flat-square&logo=appveyor "CodeFactor"