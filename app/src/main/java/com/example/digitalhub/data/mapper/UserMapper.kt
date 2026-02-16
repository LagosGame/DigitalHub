package com.example.digitalhub.data.mapper

import com.example.digitalhub.data.remote.dto.UserDto
import com.example.digitalhub.domain.model.User

fun UserDto.toDomain(): User{
    return User(
        id = this.id,
        username = this.username,
        email = this.email
    )
}