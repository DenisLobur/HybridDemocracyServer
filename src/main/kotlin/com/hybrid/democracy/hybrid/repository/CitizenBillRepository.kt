package com.hybrid.democracy.hybrid.repository

import com.hybrid.democracy.hybrid.dto.CitizenBill
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface CitizenBillRepository : JpaRepository<CitizenBill, Long> {

    @Query("SELECT cb FROM CitizenBill cb WHERE cb.citizen.citizenId = :citizenId AND cb.bill.billId = :billId")
    fun findByCitizenIdAndBillId(@Param("citizenId") citizenId: Long, @Param("billId") billId: Long): CitizenBill?
}