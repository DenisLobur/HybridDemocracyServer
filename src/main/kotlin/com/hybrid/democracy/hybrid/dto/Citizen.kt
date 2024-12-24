package com.hybrid.democracy.hybrid.dto

import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank

@Entity
@Table(name = "citizen", uniqueConstraints = [UniqueConstraint(columnNames = ["name", "email"])])
data class Citizen(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @field:NotBlank
    @Column(nullable = false, unique = true)
    val name: String,

    @field:NotBlank
    @Column(nullable = false, unique = false)
    val password: String,

    @field:NotBlank
    @Column(nullable = false, unique = true)
    val email: String

)
