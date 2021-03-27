package com.moviebox.backend.domain.repository

import com.moviebox.backend.domain.model.User

interface UserRepository {

    /**
     * Метод для создания пользователя
     *
     * @param user модель [User] пользователя с полями указанными при регистроации
     *
     * @return [User.id] уникальный идентификатор пользователя или null, если произошла ошибка или пользователя уже
     * создан
     */
    fun create(user: User): Long?

    /**
     * Метод для поиска пользователя по email
     *
     * @param email почта пользователя
     *
     * @return модель пользователя [User] или null, если пользователь не найден
     */

    fun getUserByEmail(email: String): User?

    fun getUserByUsername(username: String): User?
}
