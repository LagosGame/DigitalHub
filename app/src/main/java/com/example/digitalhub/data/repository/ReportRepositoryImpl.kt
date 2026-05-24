package com.example.digitalhub.data.repository

import com.example.digitalhub.domain.model.Report
import com.example.digitalhub.domain.repository.ReportRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class ReportRepositoryFirestoreImpl : ReportRepository {
    private val db = FirebaseFirestore.getInstance()
    private val reportesCollection = db.collection("reportes")

    override suspend fun enviarReporte(report: Report) {
        try {
            val reportMap = mapOf(
                "id" to report.id,
                "userId" to report.userId,
                "tipo" to report.tipo,
                "descripcion" to report.descripcion,
                "timestamp" to report.timestamp,
                "estado" to "Pending"
            )

            reportesCollection.document(report.id).set(reportMap).await()
        } catch (e: Exception) {
            println("Error: ${e.message}")
            throw e
        }
    }

}