package com.hybrid.democracy.hybrid.repository

import com.hybrid.democracy.hybrid.dto.Citizen
import org.springframework.data.jpa.repository.JpaRepository

interface CitizenRepository : JpaRepository<Citizen, Long> {
    fun findByName(name: String): Citizen?
    fun findByEmail(email: String): Citizen?
}