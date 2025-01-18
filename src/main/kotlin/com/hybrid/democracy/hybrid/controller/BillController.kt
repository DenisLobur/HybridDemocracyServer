package com.hybrid.democracy.hybrid.controller

import com.hybrid.democracy.hybrid.controller.CitizenController.Companion
import com.hybrid.democracy.hybrid.dto.Bill
import com.hybrid.democracy.hybrid.dto.BillDTO
import com.hybrid.democracy.hybrid.service.BillService
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/bills")
class BillController(private val billService: BillService) {

    companion object {
        private val logger: Log = LogFactory.getLog(CitizenController::class.java)
    }

    @PostMapping
    fun createBill(@RequestBody billDTO: BillDTO): ResponseEntity<Bill> {
        val bill = billService.createBill(billDTO)
        return ResponseEntity(bill, HttpStatus.CREATED)
    }

    @GetMapping("/bill/{id}")
    fun getBillById(@PathVariable id: Long): ResponseEntity<Bill> {
        val bill = billService.getBillById(id)
        return ResponseEntity(bill, HttpStatus.OK)
    }

//    @GetMapping("/citizen/{citizenId}")
//    fun getBillByCitizenId(@PathVariable citizenId: Long): ResponseEntity<List<Bill>> {
//        val bills = billService.getBillsByCitizenId(citizenId)
//        return ResponseEntity(bills, HttpStatus.OK)
//    }

    @GetMapping("/save/{citizenId}")
    fun fetchAndStoreBillData(@PathVariable citizenId: Long): ResponseEntity<Void> {
        billService.fetchAndStoreBillData(citizenId)

        return ResponseEntity(HttpStatus.OK)
    }

    @GetMapping("/pull/{citizenId}")
    fun pullBillsForCitizen(@PathVariable citizenId: Long): ResponseEntity<List<BillDTO>> {
        logger.info("hitting /pull/{$citizenId} endpoint")
        val billsList = billService.getBillsByCitizenId(citizenId)
        return ResponseEntity(billsList, HttpStatus.OK)
    }
}