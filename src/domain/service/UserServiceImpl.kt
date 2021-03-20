package com.moviebox.backend.domain.service

import com.moviebox.backend.domain.repository.UsersRepository
import com.moviebox.backend.models.User
import com.moviebox.backend.models.exception.ErrorException
import com.moviebox.backend.utils.Cipher
import com.moviebox.backend.utils.JwtProvider
import io.ktor.features.*
import java.util.*

class UserServiceImpl(private val jwtProvider: JwtProvider, private val repository: UsersRepository) : UserService {
    private val base64Encoder = Base64.getEncoder()

    override fun create(user: User): User {
        val userInDb = repository.getUserByEmail(user.email)

        if (userInDb != null) throw ErrorException.EmailAlreadyExists

        repository.create(user.copy(password = String(base64Encoder.encode(Cipher.encrypt(user.password)))))
        return user.copy(token = generateJwtToken(user), password = null)
    }

    override fun authenticate(user: User): User {
        val userFound = repository.getUserByUsername(user.username)

        return when {
            userFound == null -> throw ErrorException.UserIsNotFound
            userFound.password != user.password.encode() -> throw ErrorException.InvalidPassword
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

    private fun generateJwtToken(user: User): String {
        return jwtProvider.createJWT(username = user.username, email = user.email)
    }

    private fun String?.encode() = String(base64Encoder.encode(Cipher.encrypt(this)))
}
