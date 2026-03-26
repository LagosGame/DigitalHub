package com.example.digitalhub.domain.model

data class Comentario(
    val id: String,
    val mazoId: String,
    val autorId: String,
    val autor: User,
    val contenido: String,
    val likes: Int = 0,
    val usuariosQueDieronLike: List<String> = emptyList(),
    val timestamp: Long = System.currentTimeMillis(),
    val comentarioPadreId: String? = null,// null = comentario, not null = respuesta
    val respuestas: List<Comentario> = emptyList()
)
