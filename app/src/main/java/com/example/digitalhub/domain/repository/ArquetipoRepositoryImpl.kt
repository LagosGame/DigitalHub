package com.example.digitalhub.domain.repository

import com.example.digitalhub.data.local.FakeArquetipoDataSource
import com.example.digitalhub.domain.model.Arquetipo

class ArquetipoRepositoryImpl(
    private val dataSource: FakeArquetipoDataSource
) : ArquetipoRepository{
    override suspend fun getArquetipos(): List<Arquetipo> {
        return dataSource.getArquetipos()
    }
}