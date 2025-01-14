package com.hybrid.democracy.hybrid.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Service

@Service
class BillAnalysisService @Autowired constructor(
    private val jdbcTemplate: JdbcTemplate,
    private val sentimentAnalysisService: SentimentAnalysisService
) {

    fun analyzeAndStoreBill(billId: Int, citizenId: Int, feedback: String, rating: Int) {
        val sentiment = sentimentAnalysisService.analyzeSentiment(feedback, rating)
        val sql = "INSERT INTO bill_analysis (bill_id, citizen_id, sentiment) VALUES (?, ?, ?)"
        jdbcTemplate.update(sql, billId, citizenId, sentiment)
    }
}