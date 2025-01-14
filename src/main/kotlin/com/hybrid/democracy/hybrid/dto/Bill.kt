package com.hybrid.democracy.hybrid.dto

import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

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

    @Column(nullable = false, unique = false)
    val date: Int,

    @field:NotNull
    @Column(nullable = false, unique = true)
    val dokId: Int,

    @Column(nullable = false, unique = false)
    val orgId: Int,

    @Column(nullable = false, unique = false)
    val rating: Int,

    @Column(nullable = false, unique = false)
    val feedback: String,

    @Column(nullable = false, unique = false)
    val nreg: String,

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "citizen_id")
//    var citizen: Citizen
    val citizenId: Long
)
