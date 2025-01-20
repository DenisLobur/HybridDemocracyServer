package com.hybrid.democracy.hybrid.service

import com.hybrid.democracy.hybrid.controller.CitizenController
import com.hybrid.democracy.hybrid.dto.Bill
import com.hybrid.democracy.hybrid.dto.BillDTO
import com.hybrid.democracy.hybrid.dto.LawBillFullDTO
import com.hybrid.democracy.hybrid.mapper.BillMapper
import com.hybrid.democracy.hybrid.repository.BillRepository
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
            citizenId = billDTO.citizenId,
            dokId = 1,//billDTO.date,
            orgId = 1,//billDTO.date,
            nreg = "1",//billDTO.nreg
        )
        return billRepository.save(bill)
    }

    fun getBillById(id: Long): Bill? {
        return billRepository.findById(id).orElse(null)
    }

    fun getBillsByCitizenId(citizenId: Long): List<BillDTO> {
        val bills = billRepository.findByCitizenId(citizenId)
        return bills.map { billMapper.billToBillDTO(it) }
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
                citizenId = citizenId,
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
    fun voteBill(billId: Long, rating: Int, feedback: String) {
        val bill = billRepository.findById(billId)
            .orElseThrow { IllegalArgumentException("Bill with ID $billId not found") }

        bill.rating = rating
        bill.feedback = feedback
        bill.isVoted = true
        billRepository.save(bill)
    }
}