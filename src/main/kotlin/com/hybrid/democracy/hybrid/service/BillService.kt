package com.hybrid.democracy.hybrid.service

import com.hybrid.democracy.hybrid.dto.Bill
import com.hybrid.democracy.hybrid.dto.BillDTO
import com.hybrid.democracy.hybrid.dto.CitizenBill
import com.hybrid.democracy.hybrid.dto.LawBillFullDTO
import com.hybrid.democracy.hybrid.mapper.BillMapper
import com.hybrid.democracy.hybrid.repository.BillRepository
import com.hybrid.democracy.hybrid.repository.CitizenBillRepository
import com.hybrid.democracy.hybrid.repository.CitizenRepository
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.jsoup.Jsoup
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.client.RestTemplate

@Service
class BillService(
    private val billRepository: BillRepository,
    private val citizenRepository: CitizenRepository,
    private val citizenBillRepository: CitizenBillRepository,
    private val restTemplate: RestTemplate
) {

    companion object {
        private val logger: Log = LogFactory.getLog(BillService::class.java)
    }

    private val billMapper: BillMapper = BillMapper.INSTANCE

    @Transactional
    fun createBill(billDTO: BillDTO): Bill {
        val bill = Bill(
            title = billDTO.title,
            isVoted = billDTO.isVoted,
            date = 1,//billDTO.date,
            rating = billDTO.rating,
            feedback = billDTO.feedback,
            dokId = 1,//billDTO.date,
            orgId = 1,//billDTO.date,
            nreg = "1",//billDTO.nreg
        )

        return billRepository.save(bill)
    }

    @Transactional
    fun getBillsByCitizenId(citizenId: Long): List<Bill> {
        val citizen = citizenRepository.findById(citizenId)
            .orElseThrow { IllegalArgumentException("Citizen with ID $citizenId not found") }
        return citizen.bills.toList()
    }

    @Transactional
    fun updateBillForCitizen(billId: Long, citizenId: Long, updates: Map<String, Any>) {
        val citizenBill = citizenBillRepository.findByCitizenIdAndBillId(citizenId, billId)
            ?: throw IllegalArgumentException("Bill with ID $billId is not associated with Citizen with ID $citizenId")

            updates.forEach { (key, value) ->
                when (key) {
                    "title" -> citizenBill.title = value as String
                    //"isVoted" -> bill.isVoted = value as Boolean
                    //"date" -> bill.date = value as Int
                    //"dokId" -> bill.dokId = value as Int
                    //"orgId" -> bill.orgId = value as Int
                    "rating" -> citizenBill.rating = value as Int
                    "feedback" -> citizenBill.feedback = value as String
                    "nreg" -> citizenBill.nreg = value as String
                    else -> throw IllegalArgumentException("Invalid field: $key")
                }
            }

            citizenBillRepository.save(citizenBill)
    }

    @Transactional
    fun fetchAndStoreBillData(citizenId: Long) {
        val urlBill = "https://data.rada.gov.ua/laws/main/r[/page1].json"
        val responseBill = restTemplate.getForObject(urlBill, LawBillFullDTO::class.java)

        for (lawBill in responseBill?.list?.take(10) ?: emptyList()) {
            val bill = Bill(
                title = lawBill.nazva,
                isVoted = false,
                date = lawBill.orgdat,
                rating = 0,
                feedback = "",
                dokId = lawBill.dokid,
                orgId = lawBill.orgid,
                nreg = lawBill.nreg
            )

            billRepository.save(bill)
        }
    }

    @Transactional
    fun fetchBillTextByNReg(nreg: String): String {
        val urlText = "https://data.rada.gov.ua/laws/show/$nreg.txt"
        logger.info("Bill url: $urlText")
        val responseText = restTemplate.getForObject(urlText, String::class.java)

        val plainText = responseText?.let { Jsoup.parse(it).text() } ?: ""

        logger.info("Bill text: $plainText")
        return plainText
    }

    @Transactional
    fun voteBill(billId: Long, citizenId: Long, rating: Int, feedback: String) {
        updateBillForCitizen(billId, citizenId, mapOf("rating" to rating, "feedback" to feedback))
    }

    @Transactional
    fun getCitizenBillByCitizenIdAndBillId(citizenId: Long, billId: Long): CitizenBill? {
        return citizenBillRepository.findByCitizenIdAndBillId(citizenId, billId)
    }

    @Transactional
    fun saveBillForCitizen(billId: Long, citizenId: Long) {
        val bill = billRepository.findById(billId)
            .orElseThrow { IllegalArgumentException("Bill with ID $billId not found") }
        val citizen = citizenRepository.findById(citizenId)
            .orElseThrow { IllegalArgumentException("Citizen with ID $citizenId not found") }

        citizenBillRepository.save(
            CitizenBill(
                citizen = citizen,
                bill = bill,
                title = bill.title,
                rating = bill.rating,
                feedback = bill.feedback,
                nreg = bill.nreg
            )
        )
    }
}