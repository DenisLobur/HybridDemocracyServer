package com.hybrid.democracy.hybrid.controller

import com.hybrid.democracy.hybrid.dto.*
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

    @GetMapping("/citizen/{citizenId}/bills")
    fun getBillsByCitizenId(@PathVariable citizenId: Long): ResponseEntity<List<Bill>> {
        logger.info("hitting /citizen/{$citizenId}/bills endpoint")
        val bills = billService.getBillsByCitizenId(citizenId)
        return ResponseEntity(bills, HttpStatus.OK)
    }

    @PutMapping("/{billId}/citizen/{citizenId}")
    fun updateBillForCitizen(
        @PathVariable billId: Long,
        @PathVariable citizenId: Long,
        @RequestBody updates: Map<String, Any>
    ): ResponseEntity<Void> {
        logger.info("hitting /{$billId}/citizen/{$citizenId} endpoint")
        billService.updateBillForCitizen(billId, citizenId, updates)
        return ResponseEntity(HttpStatus.OK)
    }

    @GetMapping("/save/{citizenId}")
    fun saveBillsForCitizenById(@PathVariable citizenId: Long): ResponseEntity<Void> {
        logger.info("hitting /save/{$citizenId} endpoint")
        billService.fetchAndStoreBillData(citizenId)
        return ResponseEntity(HttpStatus.OK)
    }

//    @GetMapping("/pull/{citizenId}")
//    fun pullBillsForCitizen(@PathVariable citizenId: Long): ResponseEntity<List<BillDTO>> {
//        logger.info("hitting /pull/{$citizenId} endpoint")
//        val billsList = billService.getBillsByCitizenId(citizenId)
//        return ResponseEntity(billsList, HttpStatus.OK)
//    }

    @GetMapping("/text/{nreg}")
    fun pullBillTextByNreg(@PathVariable nreg: String): ResponseEntity<String> {
        logger.info("hitting /text/{$nreg} endpoint")
        val billText = billService.fetchBillTextByNReg(nreg)
        return ResponseEntity(billText, HttpStatus.OK)
    }

    @PostMapping("/{billId}/citizen/{citizenId}/vote")
    fun voteBill(@PathVariable billId: Long, @PathVariable citizenId: Long, @RequestBody bill: VoteDTO): ResponseEntity<Boolean> {
        logger.info("hitting voteBill endpoint with billId: $billId, citizenId: $citizenId")

        billService.voteBill(billId, citizenId, bill.rating, bill.feedback)

        return ResponseEntity(true, HttpStatus.OK)
    }

//    @GetMapping("/{billId}/citizens")
//    fun getCitizensForBill(@PathVariable billId: Long): List<Citizen>? {
//        logger.info("hitting /{$billId}/citizens endpoint")
//        return billService.findCitizensByBillId(billId)
//    }

    @PostMapping("/{billId}/citizen/{citizenId}")
    fun saveBillForCitizen(@PathVariable billId: Long, @PathVariable citizenId: Long): ResponseEntity<Void> {
        logger.info("hitting saveBillForCitizen endpoint with billId: $billId, citizenId: $citizenId")

        billService.saveBillForCitizen(billId, citizenId)
        return ResponseEntity(HttpStatus.OK)
    }

    @GetMapping("/citizen/{citizenId}/bill/{billId}")
    fun getCitizenBillByCitizenIdAndBillId(
        @PathVariable citizenId: Long,
        @PathVariable billId: Long
    ): ResponseEntity<CitizenBill> {
        logger.info("hitting /citizen/{$citizenId}/bill/{$billId} endpoint")
        val citizenBill = billService.getCitizenBillByCitizenIdAndBillId(citizenId, billId)
        return if (citizenBill != null) {
            ResponseEntity(citizenBill, HttpStatus.OK)
        } else {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }
}