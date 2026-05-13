package com.example.digitalhub.domain.repository

import com.example.digitalhub.domain.model.Report

interface ReportRepository {
    suspend fun enviarReporte(report : Report)
}