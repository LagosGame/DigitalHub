package com.example.digitalhub.domain.usecase

import com.example.digitalhub.domain.model.Report
import com.example.digitalhub.domain.repository.ReportRepository

class EnviarReporteUseCase(
    private val repository: ReportRepository
) {
    suspend operator fun invoke(report: Report) {
        repository.enviarReporte(report)
    }
}