package com.hybrid.democracy.hybrid.dto

import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank

@Entity
@Table(name = "bill")
data class Bill(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @field:NotBlank
    @Column(nullable = false, unique = false)
    val title: String,

    @Column(nullable = false, unique = false)
    val isVoted: Boolean,

    @field:NotBlank
    @Column(nullable = false, unique = false)
    val date: String,

    @field:NotBlank
    @Column(nullable = false, unique = false)
    val description: String,

    @Column(nullable = false, unique = false)
    val rating: Int,

    @Column(nullable = false, unique = false)
    val feedback: String,

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "citizen_id")
//    var citizen: Citizen
    val citizenId: Long
)
