package com.example.digitalhub.data.remote.dto

data class LoginResponseDto(
    val exito : Boolean,
    val user : UserDto?,
    val token : String?,
    val mensaje : String?
)
