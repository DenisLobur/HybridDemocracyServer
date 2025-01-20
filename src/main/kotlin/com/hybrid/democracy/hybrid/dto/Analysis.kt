package com.hybrid.democracy.hybrid.dto

import jakarta.persistence.*

@Entity
@Table(name = "bill_analysis")
data class Analysis(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false, unique = false)
    val billId: Long,

    @Column(nullable = false, unique = false)
    val citizenId: Long,

    @Column(nullable = false, unique = false)
    val sentiment: String,

    @Column(nullable = false, unique = false)
    val feedback: String,

    @Column(nullable = false, unique = false)
    val rating: Int,

    @Column(nullable = false, unique = false)
    val createdAt: Long,
)
