package com.example.digitalhub.domain.model

import com.example.digitalhub.R

data class User(
    val id : String,
    val username : String,
    val email : String,
    val iconoId: Int = R.drawable.ic_launcher_foreground,
)
