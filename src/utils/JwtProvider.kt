package com.moviebox.backend.utils

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.interfaces.DecodedJWT
import java.util.*

object JwtProvider {
    private const val validityInMs = 36_000_00 * 168 // 1 week
    const val issuer = "ktor-realworld"
    const val audience = "ktor-audience"

    val verifier: JWTVerifier = JWT
        .require(Cipher.algorithm)
        .withIssuer(issuer)
        .build()

    fun decodeJWT(token: String): DecodedJWT = JWT.require(Cipher.algorithm).build().verify(token)

    fun createJWT(username: String, email: String): String = JWT.create()
        .withIssuedAt(Date())
        .withSubject("Authentication")
        .withIssuer(issuer)
        .withAudience(audience)
        .withClaim("email", email)
        .withClaim("username", username)
        .withExpiresAt(Date(System.currentTimeMillis() + validityInMs)).sign(Cipher.algorithm)
}