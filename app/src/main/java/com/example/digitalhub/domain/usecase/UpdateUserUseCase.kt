package com.example.digitalhub.domain.usecase

import com.example.digitalhub.domain.model.User
import com.example.digitalhub.domain.repository.UserRepository

class UpdateUserUseCase(
    private val repository: UserRepository
) {
    suspend operator fun invoke(user: User): Result<Unit> {
        return try {
            repository.updateUser(user)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}