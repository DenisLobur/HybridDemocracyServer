package com.hybrid.democracy.hybrid.controller

import com.hybrid.democracy.hybrid.dto.Bill
import com.hybrid.democracy.hybrid.dto.BillDTO
import com.hybrid.democracy.hybrid.service.BillService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/bills")
class BillController(private val billService: BillService) {

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

    @GetMapping("/citizen/{citizenId}")
    fun getBillByCitizenId(@PathVariable citizenId: Long): ResponseEntity<List<Bill>> {
        val bills = billService.getBillsByCitizenId(citizenId)
        return ResponseEntity(bills, HttpStatus.OK)
    }

    @GetMapping("/fetch/{citizenId}")
    fun fetchAndStoreBillData(@PathVariable citizenId: Long): ResponseEntity<Bill> {
        val bill = billService.fetchAndStoreBillData(citizenId)
        return ResponseEntity(bill, HttpStatus.CREATED)
    }
}