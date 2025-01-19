package com.hybrid.democracy.hybrid.repository

import com.hybrid.democracy.hybrid.dto.Bill
import com.hybrid.democracy.hybrid.dto.Citizen
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface BillRepository : JpaRepository<Bill, Long> {
//    fun findById(id: Long): Bill?
    //fun findByCitizenId(citizenId: Long): List<Bill>

    @Query("SELECT b FROM Bill b WHERE b.citizenId = :citizenId")
    fun findByCitizenId(citizenId: Long): List<Bill>

    @Query("SELECT b FROM Bill b WHERE b.nreg = :nreg")
    fun findBillByNreg(nreg: String): Bill?
}