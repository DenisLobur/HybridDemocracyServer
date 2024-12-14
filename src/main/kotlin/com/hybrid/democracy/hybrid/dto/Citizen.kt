package com.hybrid.democracy.hybrid.dto

import jakarta.persistence.*

@Entity
data class Citizen(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    val name: String,
    val password: String,
    val email: String

)
