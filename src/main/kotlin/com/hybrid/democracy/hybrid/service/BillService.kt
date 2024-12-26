package com.hybrid.democracy.hybrid.service

import com.hybrid.democracy.hybrid.dto.Bill
import com.hybrid.democracy.hybrid.dto.BillDTO
import com.hybrid.democracy.hybrid.repository.BillRepository
import com.hybrid.democracy.hybrid.repository.CitizenRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.client.RestTemplate

@Service
class BillService(
    private val billRepository: BillRepository,
    private val citizenRepository: CitizenRepository,
    private val restTemplate: RestTemplate
) {

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
            date = billDTO.date,
            description = billDTO.description,
            rating = billDTO.rating,
            feedback = billDTO.feedback,
            citizenId = billDTO.citizenId
        )
        return billRepository.save(bill)
    }

    fun getBillById(id: Long): Bill? {
        return billRepository.findById(id).orElse(null)
    }

    fun getBillsByCitizenId(citizenId: Long): List<Bill> {
        return billRepository.findByCitizenId(citizenId)
    }

    @Transactional
    fun fetchAndStoreBillData(citizenId: Long): Bill {
        val url = "https://data.rada.gov.ua/laws/main/r.txt"
        val response = restTemplate.getForObject(url, String::class.java)

        val citizen = citizenRepository.findById(citizenId)
            .orElseThrow { IllegalArgumentException("Citizen with ID $citizenId not found") }

        val bill = Bill(
            title = "Fetched Bill 2",
            isVoted = false,
            date = "2023-10-01", // Example date, you can parse the actual date from the response if needed
            description = response?.take(100) ?: "No description",
            rating = 0,
            feedback = "",
            citizenId = citizenId
        )
        return billRepository.save(bill)
    }
}