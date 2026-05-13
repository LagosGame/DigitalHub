package com.example.digitalhub.domain.repository

import com.example.digitalhub.domain.model.User

interface UserRepository {
    suspend fun getUserById(userId: String): User?
    suspend fun updateUser(user: User)
    suspend fun isUsernameAvailable(username: String): Boolean
}