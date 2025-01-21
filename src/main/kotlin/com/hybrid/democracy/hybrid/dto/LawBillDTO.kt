package com.hybrid.democracy.hybrid.dto

import jakarta.validation.constraints.NotNull

data class LawBillFullDTO(
    val cnt: Int,
    val from: Int,
    val list: List<LawBillDTO>,

) {
    data class LawBillDTO(
        @NotNull val dokid: Int,
        val orgid: Int,
        val nazva: String,
        val nreg: String,
        val num: Int,
        val orgdat: Int,
    )
}