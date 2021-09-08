package com.moviebox.backend.domain.services.user

import com.moviebox.backend.domain.encrypt.BCrypt
import com.moviebox.backend.domain.encrypt.JwtProvider
import com.moviebox.backend.domain.model.ErrorException
import com.moviebox.backend.domain.model.User
import com.moviebox.backend.domain.repository.UserRepository
import io.ktor.features.NotFoundException
import java.util.Base64

class UserServiceImpl(private val jwtProvider: JwtProvider, private val repository: UserRepository) : UserService {
    private val base64Encoder = Base64.getEncoder()

    override fun create(user: User): User {
        val userInDb = repository.getUserByEmail(user.email)

        if (userInDb != null) throw ErrorException.EmailAlreadyExists

        repository.create(user.copy(password = BCrypt.encrypt(user.password!!)))
        return user.copy(token = generateJwtToken(user), password = null)
    }

    override fun authenticate(user: User): User {
        val userFound = repository.getUserByUsername(user.username)

        return when {
            userFound == null -> throw ErrorException.UserIsNotFound
            userFound.password == null -> throw ErrorException.UserIsNotFound
            BCrypt.verify(user.password!!, userFound.password) -> throw ErrorException.InvalidPassword
            else -> {
                userFound.copy(token = generateJwtToken(userFound), password = null)
            }
        }
    }

    override fun getByEmail(email: String): User {
        val user = repository.getUserByEmail(email)
        user ?: throw NotFoundException("User not found to get.")
        return user.copy(token = generateJwtToken(user))
    }

    private fun generateJwtToken(user: User): String =
        jwtProvider.createJWT(username = user.username, email = user.email)
}
