package com.hybrid.democracy.hybrid.dto

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

@Entity
@Table(name = "bill")
data class Bill(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val billId: Long = 0,

    @Column
    var title: String?,

    @Column
    var isVoted: Boolean?,

    @Column
    var date: Int?,

    @Column
    var dokId: Int?,

    @Column
    var orgId: Int?,

    @Column
    var rating: Int?,

    @Column
    var feedback: String?,

    @Column
    var nreg: String?,

    @JsonIgnore
//    @ManyToOne
//    @JoinColumn(name = "citizen_id")
//    var citizen: Citizen? = null
    @ManyToMany(mappedBy = "bills")
    var citizens: MutableList<Citizen> = mutableListOf()
)
