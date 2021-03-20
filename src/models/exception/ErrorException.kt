package com.moviebox.backend.models.exception

import com.google.gson.annotations.SerializedName

sealed class ErrorException : Exception() {
    object InvalidAuthorizationData : ErrorException()
    object UsernameAlreadyExists : ErrorException()
    object EmailAlreadyExists : ErrorException()
    object InvalidPassword : ErrorException()
    object UserIsNotFound : ErrorException()
}

data class ErrorMessage(
    @SerializedName("status_code") val statusCode: Int,
    val notLocalizedMessage: String
) {
    override fun toString(): String = "$statusCode $notLocalizedMessage"

    override fun equals(other: Any?): Boolean = other is ErrorMessage && other.statusCode == statusCode

    override fun hashCode(): Int = statusCode.hashCode()

    companion object {
        val InvalidAuthorizationData = ErrorMessage(
            statusCode = 2, notLocalizedMessage = "Invalid username and/or password: You did not provide a valid login."
        )

        val UsernameAlreadyExists = ErrorMessage(
            statusCode = 3, notLocalizedMessage = "User with this login already exists."
        )

        val EmailAlreadyExists = ErrorMessage(
            statusCode = 4, notLocalizedMessage = "A user with the same email address already exists."
        )

        val InvalidPassword = ErrorMessage(
            statusCode = 5, notLocalizedMessage = "Invalid password."
        )

        val UserIsNotFound = ErrorMessage(
            statusCode = 6, notLocalizedMessage = "User is not found."
        )
    }
}
