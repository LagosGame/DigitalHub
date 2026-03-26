package com.example.digitalhub.data.local

import com.example.digitalhub.domain.model.User
import com.example.digitalhub.R
import com.example.digitalhub.domain.model.Comentario

class FakeComentarioDataSource {
    private val usuarios = listOf(
        User(
            id = "user1",
            username = "PeraUnMomento",
            iconoId = R.drawable.ic_launcher_foreground,
            email = ""
        ),
        User(
            id = "user2",
            username = "PlatanoPlayer",
            iconoId = R.drawable.ic_launcher_foreground,
            email = ""
        ),
        User(
            id = "user3",
            username = "CerezaGamer",
            iconoId = R.drawable.ic_launcher_foreground,
            email = ""
        )
    )

    private val comentarios = mutableListOf(
        Comentario(
            id = "com1",
            mazoId = "1",
            autorId = "user1",
            autor = usuarios[0],
            contenido = "Yo utilizaría quizás el Wargreymon del ST-1",
            likes = 6,
            usuariosQueDieronLike = listOf("user2", "user3"),
            timestamp = System.currentTimeMillis() - 3600000,
            respuestas = listOf(
                Comentario(
                    id = "com2",
                    mazoId = "1",
                    autorId = "user2",
                    autor = usuarios[1],
                    contenido = "No es mala idea pero el de BT1 da mejores resultados",
                    likes = 12,
                    timestamp = System.currentTimeMillis() - 1800000,  // Hace 30 min
                    comentarioPadreId = "com1"
                )
            )
        ),
        Comentario(
            id = "com3",
            mazoId = "1",
            autorId = "user3",
            autor = usuarios[2],
            contenido = "Excelente mazo! Lo probé y funciona muy bien",
            likes = 8,
            usuariosQueDieronLike = listOf("user1", "user2"),
            timestamp = System.currentTimeMillis() - 7200000  // Hace 2 horas
        )
    )

    fun getComentariosPorMazo(mazoId: String): List<Comentario> {
        return comentarios.filter { it.mazoId == mazoId && it.comentarioPadreId == null }
    }

    fun agregarComentario(comentario: Comentario) {
        comentarios.add(comentario)
    }

    fun toggleLike(comentarioId: String,usuarioId:String) {
        val index = comentarios.indexOfFirst { it.id == comentarioId }
        if (index != -1) {
            val comentario = comentarios[index]
            val yaLeDioLike = comentario.usuariosQueDieronLike.contains(usuarioId)

            comentarios[index] = if (yaLeDioLike) {
                comentario.copy(
                    likes = comentario.likes - 1,
                    usuariosQueDieronLike = comentario.usuariosQueDieronLike - usuarioId
                )
            } else {
                comentario.copy(
                    likes = comentario.likes + 1,
                    usuariosQueDieronLike = comentario.usuariosQueDieronLike + usuarioId
                )
            }
        }
    }

    fun getUsuarioById(id: String): User? {
        return usuarios.find { it.id == id }
    }
}