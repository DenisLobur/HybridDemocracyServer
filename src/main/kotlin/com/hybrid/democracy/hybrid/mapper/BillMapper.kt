package com.hybrid.democracy.hybrid.mapper

import com.hybrid.democracy.hybrid.dto.Bill
import com.hybrid.democracy.hybrid.dto.BillDTO
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.factory.Mappers

@Mapper(componentModel = "spring")
interface BillMapper {
    companion object {
        val INSTANCE: BillMapper = Mappers.getMapper(BillMapper::class.java)
    }

    @Mapping(target = "date", source = "date")
    fun billToBillDTO(bill: Bill): BillDTO
    fun billDTOToBill(billDTO: BillDTO): Bill
    fun billDTOListToBillList(billDTOList: List<BillDTO>): List<Bill>
    fun billListToBillDTOList(billList: List<Bill>): List<BillDTO>
}