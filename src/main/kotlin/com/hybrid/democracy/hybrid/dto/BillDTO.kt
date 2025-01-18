package com.hybrid.democracy.hybrid.dto

data class BillDTO(
    val title: String,
    val isVoted: Boolean,
    val date: Int,
    val dokId: Int,
    val orgId: Int,
    val rating: Int,
    val feedback: String,
    val nreg: String,
    val citizenId: Long
)