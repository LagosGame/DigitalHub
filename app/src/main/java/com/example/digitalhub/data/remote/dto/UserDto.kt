package com.example.digitalhub.data.remote.dto

data class UserDto(
    val id : String,
    val username: String,
    val email : String,
    val creadoEn: String? = null
)
