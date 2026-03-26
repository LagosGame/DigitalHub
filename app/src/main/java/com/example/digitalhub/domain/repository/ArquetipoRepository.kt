package com.example.digitalhub.domain.repository

import com.example.digitalhub.domain.model.Arquetipo

interface ArquetipoRepository {
    suspend fun getArquetipos(): List<Arquetipo>
}