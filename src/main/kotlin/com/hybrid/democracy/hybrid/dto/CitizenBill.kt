package com.hybrid.democracy.hybrid.dto

import jakarta.persistence.*

@Entity
@Table(name = "citizen_bill")
data class CitizenBill(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @ManyToOne
    @JoinColumn(name = "citizen_id")
    val citizen: Citizen,

    @ManyToOne
    @JoinColumn(name = "bill_id")
    val bill: Bill,

    @Column
    var title: String?,

    @Column
    var rating: Int?,

    @Column
    var feedback: String?,

    @Column
    var sentiment: String?,

    @Column
    var nreg: String?,

)
