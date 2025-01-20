package com.hybrid.democracy.hybrid.controller

import com.hybrid.democracy.hybrid.dto.Bill
import com.hybrid.democracy.hybrid.dto.BillDTO
import com.hybrid.democracy.hybrid.dto.VoteDTO
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
    fun saveBillsForCitizenById(@PathVariable citizenId: Long): ResponseEntity<Void> {
        logger.info("hitting /save/{$citizenId} endpoint")
        billService.fetchAndStoreBillData(citizenId)
        return ResponseEntity(HttpStatus.OK)
    }

    @GetMapping("/pull/{citizenId}")
    fun pullBillsForCitizen(@PathVariable citizenId: Long): ResponseEntity<List<BillDTO>> {
        logger.info("hitting /pull/{$citizenId} endpoint")
        val billsList = billService.getBillsByCitizenId(citizenId)
        return ResponseEntity(billsList, HttpStatus.OK)
    }

    @GetMapping("/text/{nreg}")
    fun pullBillTextByNreg(@PathVariable nreg: String): ResponseEntity<String> {
        logger.info("hitting /text/{$nreg} endpoint")
        val billText = billService.fetchBillTextByNReg(nreg)
        return ResponseEntity(billText, HttpStatus.OK)
    }

    @PostMapping("/vote/{billId}")
    fun voteBill(@PathVariable billId: Long, @RequestBody bill: VoteDTO): ResponseEntity<Boolean> {
        logger.info("hitting /vote/{$billId} endpoint")
        billService.voteBill(billId, bill.rating, bill.feedback)
        return ResponseEntity(true, HttpStatus.OK)
    }
}