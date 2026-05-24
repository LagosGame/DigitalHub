package com.example.digitalhub.domain.usecase

import com.example.digitalhub.domain.model.User
import com.example.digitalhub.domain.repository.AutentificacionRepository
import com.example.digitalhub.domain.repository.UserRepository


class GetCurrentUserUseCase(
    private val userRepository: UserRepository,
    private val authRepository: AutentificacionRepository
) {
     suspend operator fun invoke(): User? {
         val currentAuthUser = authRepository.getCurrentUser()
         val userId = currentAuthUser?.id ?: return null
         return userRepository.getUserById(userId)
    }
}