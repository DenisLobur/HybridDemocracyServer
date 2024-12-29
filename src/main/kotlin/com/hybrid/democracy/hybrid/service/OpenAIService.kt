package com.hybrid.democracy.hybrid.service

import org.springframework.ai.chat.model.ChatModel
import org.springframework.ai.chat.prompt.PromptTemplate
import org.springframework.stereotype.Service

@Service
class OpenAIService(private val chatModel: ChatModel) {

    fun getOpenAIResponse(question: String): String {
        val promptTemplate = PromptTemplate(question)
        val prompt = promptTemplate.create()
        val response = chatModel.call(prompt)

        return response.result.output.content
    }
}