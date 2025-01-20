package com.hybrid.democracy.hybrid.dto

data class AnalysisDTO(
    val billId: Long,
    val citizenId: Long,
    val rating: Int,
    val feedback: String
)