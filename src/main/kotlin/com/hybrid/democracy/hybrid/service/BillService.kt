package com.hybrid.democracy.hybrid.service

import com.hybrid.democracy.hybrid.controller.CitizenController
import com.hybrid.democracy.hybrid.dto.*
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
//        if (billRepository.findByBillId(billDTO.date) != null) {
//            throw IllegalArgumentException("Email ${citizenDTO.email} already in use")
//        }

        val citizen = citizenRepository.findById(billDTO.citizenId)
            .orElseThrow { IllegalArgumentException("Citizen with ID ${billDTO.citizenId} not found") }

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
//        val citizen = citizenRepository.findById(citizenId)
//            .orElseThrow { IllegalArgumentException("Citizen with ID $citizenId not found") }
//        val bill = billRepository.findById(billId)
//            .orElseThrow { IllegalArgumentException("Bill with ID $billId not found") }
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
//            citizenBillRepository.save(
//                CitizenBill(
//                    citizen = citizen,
//                    bill = bill,
//                    title = bill.title,
//                    rating = bill.rating,
//                    feedback = bill.feedback
//                )
//            )
    }

    @Transactional
    fun fetchAndStoreBillData(citizenId: Long) {
        val urlBill = "https://data.rada.gov.ua/laws/main/r[/page1].json"
        val responseBill = restTemplate.getForObject(urlBill, LawBillFullDTO::class.java)


//        val citizen = citizenRepository.findById(citizenId)
//            .orElseThrow { IllegalArgumentException("Citizen with ID $citizenId not found") }

        for (lawBill in responseBill?.list?.take(10) ?: emptyList()) {
//            val urlText = "https://data.rada.gov.ua/laws/show/${lawBill.nreg}.txt"
//            val responseText = restTemplate.getForObject(urlText, String::class.java)
//
//            logger.info("Bill url: $urlText")

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

            // TODO: Make sure only unique dok_id is saved
            billRepository.save(bill)
            //Thread.sleep(6000)
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

//        val bill: Bill? = billRepository.findBillByNreg(nreg)
//
//        if (bill != null) {
//            bill.feedback = urlText
//            billRepository.save(bill)
//        }
//
    }

    @Transactional
    fun voteBill(billId: Long, citizenId: Long, rating: Int, feedback: String) {
//        val bill = billRepository.findById(billId)
//            .orElseThrow { IllegalArgumentException("Bill with ID $billId not found") }
//
//        bill.rating = rating
//        bill.feedback = feedback
//        bill.isVoted = true
//        billRepository.save(bill)
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

//        citizen.bills += bill
//        citizenRepository.save(citizen)
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