package com.hybrid.democracy.hybrid.service

import com.hybrid.democracy.hybrid.dto.Bill
import com.hybrid.democracy.hybrid.dto.BillDTO
import com.hybrid.democracy.hybrid.dto.Citizen
import com.hybrid.democracy.hybrid.dto.CitizenDTO
import com.hybrid.democracy.hybrid.repository.BillRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class BillService(private val billRepository: BillRepository) {

    @Transactional
    fun createBill(billDTO: BillDTO): Bill {
//        if (billRepository.findByBillId(billDTO.date) != null) {
//            throw IllegalArgumentException("Email ${citizenDTO.email} already in use")
//        }

        val bill = Bill(
            title = billDTO.title,
            isVoted = billDTO.isVoted,
            date = billDTO.date
        )
        return billRepository.save(bill)
    }

    fun getBillById(id: Long): Bill? {
        return billRepository.findByBillId(id)
    }

    fun getBillsByCitizenId(citizenId: Long): List<Bill> {
        return billRepository.findByCitizenId(citizenId)
    }
}