package com.moviebox.backend.domain.encrypt

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.interfaces.DecodedJWT
import java.util.Date

object JwtProvider {
    private const val validityInMs = 36_000_00 * 168 // 1 week
    const val issuer = "ktor-realworld"
    const val audience = "ktor-audience"

    private const val subject = "Authentication"

    private const val claim_email = "email"
    private const val claim_username = "username"

    val verifier: JWTVerifier = JWT
        .require(Cipher.algorithm)
        .withIssuer(issuer)
        .build()

    fun decodeJWT(token: String): DecodedJWT = JWT.require(Cipher.algorithm).build().verify(token)

    fun createJWT(username: String, email: String): String = JWT.create()
        .withIssuedAt(Date())
        .withSubject(subject)
        .withIssuer(issuer)
        .withAudience(audience)
        .withClaim(claim_email, email)
        .withClaim(claim_username, username)
        .withExpiresAt(Date(System.currentTimeMillis() + validityInMs)).sign(Cipher.algorithm)
}
