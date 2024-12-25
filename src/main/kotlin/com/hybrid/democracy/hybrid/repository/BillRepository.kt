package com.hybrid.democracy.hybrid.repository

import com.hybrid.democracy.hybrid.dto.Bill
import com.hybrid.democracy.hybrid.dto.Citizen
import org.springframework.data.jpa.repository.JpaRepository

interface BillRepository : JpaRepository<Bill, Long> {
    fun findByBillId(id: Long): Bill?
    fun findByCitizenId(citizenId: Long): List<Bill>
}