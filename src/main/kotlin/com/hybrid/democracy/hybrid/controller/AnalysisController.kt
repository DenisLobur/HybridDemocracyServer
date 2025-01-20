package com.hybrid.democracy.hybrid.controller

import com.hybrid.democracy.hybrid.dto.AnalysisDTO
import com.hybrid.democracy.hybrid.service.AnalysisService
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/sentiment")
class AnalysisController(private val analysisService: AnalysisService) {

    companion object {
        private val logger: Log = LogFactory.getLog(AnalysisController::class.java)
    }

    @PostMapping
    fun analyzeSentiment(@RequestBody request: AnalysisDTO): ResponseEntity<Boolean> {
        val sentimentAnalysisResult = analysisService.analyzeAndStoreBill(
            billId = request.billId,
            citizenId = request.citizenId,
            feedback = request.feedback,
            rating = request.rating
        )

        logger.info("hitting /sentiment endpoint")
        return ResponseEntity(true, HttpStatus.OK)
    }
}