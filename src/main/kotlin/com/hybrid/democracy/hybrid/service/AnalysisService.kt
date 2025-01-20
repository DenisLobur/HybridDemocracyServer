package com.hybrid.democracy.hybrid.service

import com.hybrid.democracy.hybrid.dto.Analysis
import com.hybrid.democracy.hybrid.repository.AnalysisRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Service

@Service
class AnalysisService @Autowired constructor(
    private val analysisRepository: AnalysisRepository,
) {

    fun analyzeAndStoreBill(billId: Long, citizenId: Long, feedback: String, rating: Int) {
        val sentiment = analyze(feedback, rating)
        val existingAnalysis = analysisRepository.findByBillIdAndCitizenId(billId, citizenId)

        val analysis = existingAnalysis?.copy(
            sentiment = sentiment,
            feedback = feedback,
            rating = rating,
            createdAt = (System.currentTimeMillis() / 1000)
        ) ?: Analysis(
            billId = billId,
            citizenId = citizenId,
            sentiment = sentiment,
            feedback = feedback,
            rating = rating,
            createdAt = (System.currentTimeMillis() / 1000)
        )

        analysisRepository.save(analysis)
    }


    private fun analyze(feedback: String, rating: Int): String {
        val basedOnFeedback = when {
            feedback.contains("good", ignoreCase = true) -> "positive"
            feedback.contains("bad", ignoreCase = true) -> "negative"
            else -> "neutral"
        }

        val basedOnRating =  when {
            rating > 3 -> "positive"
            rating < 3 -> "negative"
            else -> "neutral"
        }

        return if (basedOnRating == basedOnFeedback) {
            basedOnRating
        } else {
            "neutral"
        }
    }
}