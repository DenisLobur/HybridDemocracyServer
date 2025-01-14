package com.hybrid.democracy.hybrid.controller

import com.hybrid.democracy.hybrid.service.OpenAIService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/openai")
class OpenAIController(private val openAIService: OpenAIService) {

    @GetMapping("/response")
    fun getResponse(@RequestParam question: String): String {
        return openAIService.getOpenAIResponse(question)
    }

    @PostMapping("/summarize")
    fun summarizeText(@RequestBody longText: String): String {
        return openAIService.summarizeText(longText)
    }
}