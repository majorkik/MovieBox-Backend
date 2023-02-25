package com.moviebox.backend.data

import com.moviebox.backend.data.repository.UserRepositoryImpl
import com.moviebox.backend.domain.repository.UserRepository
import org.koin.dsl.module

val dataModule = module {
    single<UserRepository> { UserRepositoryImpl() }
}
