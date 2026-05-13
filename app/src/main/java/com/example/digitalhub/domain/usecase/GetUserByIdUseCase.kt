package com.example.digitalhub.domain.usecase

import com.example.digitalhub.domain.model.User
import com.example.digitalhub.domain.repository.UserRepository

class GetUserByIdUseCase(
    private val repository: UserRepository
) {
    suspend operator fun invoke(userId: String): User? {
        return repository.getUserById(userId)
    }
}