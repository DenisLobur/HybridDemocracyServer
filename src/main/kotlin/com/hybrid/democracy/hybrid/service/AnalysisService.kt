package com.hybrid.democracy.hybrid.service

import com.hybrid.democracy.hybrid.dto.Analysis
import com.hybrid.democracy.hybrid.repository.AnalysisRepository
import com.hybrid.democracy.hybrid.repository.CitizenBillRepository
import org.springframework.ai.chat.model.ChatModel
import org.springframework.ai.chat.prompt.PromptTemplate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class AnalysisService @Autowired constructor(
    private val chatModel: ChatModel,
    private val citizenBillRepository: CitizenBillRepository,
) {

    fun getOpenAIResponse(question: String): String {
        val promptTemplate = PromptTemplate(question)
        val prompt = promptTemplate.create()
        val response = chatModel.call(prompt)

        return response.result.output.content
    }

    fun summarizeText(longText: String): String {
        val promptTemplate = PromptTemplate("Скороти текст і дай коротке тлумачення українською мовою: $longText")
        val prompt = promptTemplate.create()
        val response = chatModel.call(prompt)

        return response.result.output.content
    }

    fun analyzeAndStoreSentiment(billId: Long, citizenId: Long, feedback: String, rating: Int): String {
        val promptTemplate = PromptTemplate(
            "Based on the feedback=${feedback} and rating=${rating}, determine the sentiment of the citizen towards the bill." +
                    " Make it one word: 'good', 'bad', or 'neutral'")
        val prompt = promptTemplate.create()
        val response = chatModel.call(prompt)

        return response.result.output.content
    }


//    private fun analyze(feedback: String, rating: Int): String {
//        val basedOnFeedback = when {
//            feedback.contains("good", ignoreCase = true) -> "positive"
//            feedback.contains("bad", ignoreCase = true) -> "negative"
//            else -> "neutral"
//        }
//
//        val basedOnRating =  when {
//            rating > 3 -> "positive"
//            rating < 3 -> "negative"
//            else -> "neutral"
//        }
//
//        return if (basedOnRating == basedOnFeedback) {
//            basedOnRating
//        } else {
//            "neutral"
//        }
//    }
}