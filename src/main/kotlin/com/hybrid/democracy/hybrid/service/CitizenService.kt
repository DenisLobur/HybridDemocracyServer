package com.hybrid.democracy.hybrid.service

import com.hybrid.democracy.hybrid.dto.Bill
import com.hybrid.democracy.hybrid.dto.Citizen
import com.hybrid.democracy.hybrid.dto.CitizenDTO
import com.hybrid.democracy.hybrid.repository.CitizenRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CitizenService(private val citizenRepository: CitizenRepository) {

    @Transactional
    fun createCitizen(citizenDTO: CitizenDTO): Citizen {
        if (citizenRepository.findByEmail(citizenDTO.email) != null) {
            throw IllegalArgumentException("Email ${citizenDTO.email} already in use")
        }

        val citizen = Citizen(
            name = citizenDTO.name,
            email = citizenDTO.email,
            password = citizenDTO.password
        )
        return citizenRepository.save(citizen)
    }

    fun authenticate(email: String, password: String): Citizen? {
        val citizen = citizenRepository.findByEmail(email)
        if (citizen == null || citizen.password != password) {
            return null
        }

        return citizen
    }

    fun getCitizenByName(name: String): Citizen? {
        return citizenRepository.findByName(name)
    }

    fun getCitizenByEmail(email: String): Citizen? {
        return citizenRepository.findByEmail(email)
    }

    fun findBillsByCitizenId(citizenId: Long): List<Bill>? {
        return citizenRepository.findById(citizenId).orElse(null)?.bills
    }

}