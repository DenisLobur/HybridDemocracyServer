package com.hybrid.democracy.hybrid.repository

import com.hybrid.democracy.hybrid.dto.Bill
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface BillRepository : JpaRepository<Bill, Long> {

    @Query("SELECT b FROM Bill b WHERE b.nreg = :nreg")
    fun findBillByNreg(nreg: String): Bill?
}