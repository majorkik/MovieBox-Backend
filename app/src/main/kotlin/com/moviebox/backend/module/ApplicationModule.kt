package com.moviebox.backend.module

import com.moviebox.backend.module.user.UserController
import org.koin.dsl.module

val applicationModule = module {
    single { UserController(get()) }
}
