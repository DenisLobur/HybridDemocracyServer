package com.hybrid.democracy.hybrid.service

import org.springframework.stereotype.Service

@Service
class SentimentAnalysisService {

    fun analyzeSentiment(feedback: String, rating: Int): String {
        val basedOnRating =  when {
            rating > 3 -> "positive"
            rating < 3 -> "negative"
            else -> "neutral"
        }

        val basedOnFeedback = when {
            feedback.contains("good", ignoreCase = true) -> "positive"
            feedback.contains("bad", ignoreCase = true) -> "negative"
            else -> "neutral"
        }

        return if (basedOnRating == basedOnFeedback) {
            basedOnRating
        } else {
            "neutral"
        }
    }
}