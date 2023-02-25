package com.moviebox.backend.domain

import com.moviebox.backend.domain.encrypt.JwtProvider
import com.moviebox.backend.domain.services.user.UserService
import com.moviebox.backend.domain.services.user.UserServiceImpl
import org.koin.dsl.module

val domainModule = module {
    single<UserService> { UserServiceImpl(JwtProvider, get()) }
}
