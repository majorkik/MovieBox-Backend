package com.moviebox.backend.domain.encrypt

import at.favre.lib.crypto.bcrypt.BCrypt

object BCrypt {
    fun encrypt(password: String): String = BCrypt.withDefaults().hashToString(12, password.toCharArray())

    fun verify(password: String, bcryptPasswordHash: String): Boolean = BCrypt.verifyer().verify(
        password.toCharArray(),
        bcryptPasswordHash
    ).verified
}
