package com.moviebox.backend.di

import com.moviebox.backend.domain.repository.UsersRepository
import com.moviebox.backend.domain.service.UserService
import com.moviebox.backend.domain.service.UserServiceImpl
import com.moviebox.backend.utils.JwtProvider
import com.moviebox.backend.web.controllers.UserController
import org.koin.dsl.module

val applicationModule = module {

    /**
     * Controllers
     */

    single { UserController(get()) }

    /**
     * Services
     */
    single<UserService> { UserServiceImpl(JwtProvider, get()) }

    /**
     * Repositories
     */
    single { UsersRepository() }
}
