package com.hybrid.democracy.hybrid.controller

import com.hybrid.democracy.hybrid.controller.HomeController.Companion
import com.hybrid.democracy.hybrid.dto.Bill
import com.hybrid.democracy.hybrid.dto.Citizen
import com.hybrid.democracy.hybrid.dto.CitizenDTO
import com.hybrid.democracy.hybrid.service.CitizenService
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/citizens")
class CitizenController(private val citizenService: CitizenService) {

    companion object {
        private val logger: Log = LogFactory.getLog(CitizenController::class.java)
    }

    @PostMapping
    fun createCitizen(@RequestBody citizenDTO: CitizenDTO): ResponseEntity<Citizen> {
        val citizen = citizenService.createCitizen(citizenDTO)
        return ResponseEntity(citizen, HttpStatus.CREATED)
    }

    @GetMapping("/name/{name}")
    fun getCitizenByName(@PathVariable name: String): ResponseEntity<Citizen> {
        val citizen = citizenService.getCitizenByName(name)
        return ResponseEntity(citizen, HttpStatus.OK)
    }

    @GetMapping("/email/{email}")
    fun getCitizenByEmail(@PathVariable email: String): ResponseEntity<Citizen> {
        logger.info("hitting /email/{$email} endpoint")
        val citizen = citizenService.getCitizenByEmail(email)
        return ResponseEntity(citizen, HttpStatus.OK)
    }

    @GetMapping("/{citizenId}/bills")
    fun getBillsForCitizen(@PathVariable citizenId: Long): List<Bill>? {
        logger.info("hitting /{$citizenId}/bills endpoint")
        return citizenService.findBillsByCitizenId(citizenId)
    }
}