package com.hybrid.democracy.hybrid.dto

data class BillDTO(
    val title: String,
    val isVoted: Boolean,
    val date: String,
    val description: String,
    val rating: Int,
    val feedback: String,
    val citizenId: Long
)