package com.hybrid.democracy.hybrid.repository

import com.hybrid.democracy.hybrid.dto.Analysis
import org.springframework.data.jpa.repository.JpaRepository

interface AnalysisRepository: JpaRepository<Analysis, Long> {
    fun findByBillIdAndCitizenId(billId: Long, citizenId: Long): Analysis?
}