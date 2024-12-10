package com.example.demo_web.utils;

import com.example.demo_web.dto.HotelContractDto;
import com.example.demo_web.model.HotelContract;

public class ContractMapper
{
    public static HotelContractDto entityToDto( HotelContract contract)
    {
        HotelContractDto dto = new HotelContractDto();
        dto.setContractId( contract.getContractId() );
        dto.setHotelName( contract.getHotelName() );
        dto.setContractValidFrom((contract.getContractValidFrom()));
        dto.setContractValidTo(contract.getContractValidTo());
        dto.setMarkupPercentage(contract.getMarkupPercentage());

        return dto;
    }

    public static HotelContract dtoToEntity(HotelContractDto dto)
    {
        HotelContract contract = new HotelContract();
        contract.setContractId( dto.getContractId() );
        contract.setHotelName( dto.getHotelName() );
        contract.setContractValidFrom(dto.getContractValidFrom());
        contract.setContractValidTo(dto.getContractValidTo());
        contract.setMarkupPercentage(dto.getMarkupPercentage());
        return contract;
    }
}
