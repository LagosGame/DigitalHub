package com.example.digitalhub.domain.model

data class Report(
    val id: String = "",
    val userId: String = "",
    val tipo: String = "Bug",
    val descripcion: String = "",
    val timestamp: Long = System.currentTimeMillis()
)
