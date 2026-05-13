package com.example.digitalhub.domain.usecase

import com.example.digitalhub.domain.repository.UserRepository

class CheckUsuarioUseCase(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(username: String): Boolean {
        return userRepository.isUsernameAvailable(username)
    }
}