package com.hybrid.democracy.hybrid.dto

import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank

@Entity
@Table(name = "bill", uniqueConstraints = [UniqueConstraint(columnNames = ["id", "title"])])
data class Bill(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @field:NotBlank
    @Column(nullable = false, unique = true)
    val title: String,

    @field:NotBlank
    @Column(nullable = false, unique = false)
    val isVoted: Boolean,

    @field:NotBlank
    @Column(nullable = false, unique = false)
    val date: String

)
