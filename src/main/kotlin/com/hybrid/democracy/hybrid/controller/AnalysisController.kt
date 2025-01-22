package com.hybrid.democracy.hybrid.controller

import com.hybrid.democracy.hybrid.service.AnalysisService
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/analyze")
class AnalysisController(private val analysisService: AnalysisService) {

    companion object {
        private val logger: Log = LogFactory.getLog(AnalysisController::class.java)
    }

    @PostMapping("/summarize")
    fun summarizeText(@RequestBody longText: String): String {
        logger.info("hitting /summarize endpoint")
        val sanitizedText = longText.replace("\r\n", " ").replace("\n", " ").replace(Regex("[^A-Za-z0-9]"), " ")

        logger.info("sanitized: $sanitizedText")

        return analysisService.summarizeText(sanitizedText)
    }
}