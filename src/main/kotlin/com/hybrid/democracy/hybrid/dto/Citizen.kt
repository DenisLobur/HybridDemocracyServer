package com.hybrid.democracy.hybrid.dto

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank

@Entity
@Table(name = "citizen", uniqueConstraints = [UniqueConstraint(columnNames = ["name", "email"])])
data class Citizen(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val citizenId: Long = 0,

    @field:NotBlank
    @Column(nullable = false, unique = true)
    val name: String,

    @field:NotBlank
    @Column(nullable = false, unique = false)
    val password: String,

    @field:NotBlank
    @Column(nullable = false, unique = true)
    val email: String,

    @JsonIgnore
//    @OneToMany(mappedBy = "citizen", cascade = [CascadeType.ALL], orphanRemoval = true)
//    var bills: MutableList<Bill> = mutableListOf()
    @ManyToMany
    @JoinTable(
        name = "citizen_bill",
        joinColumns = [JoinColumn(name = "citizen_id")],
        inverseJoinColumns = [JoinColumn(name = "bill_id")]
    )
    var bills: MutableList<Bill> = mutableListOf()

)
