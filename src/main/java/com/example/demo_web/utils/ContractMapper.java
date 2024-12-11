package com.example.demo_web.utils;

import com.example.demo_web.dto.HotelContractDto;
import com.example.demo_web.model.HotelContract;

import java.util.stream.Collectors;

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

        // Map room types
        dto.setRoomTypes(contract.getRoomTypes().stream().map(roomType -> {
            HotelContractDto.RoomTypeDto roomTypeDto = new HotelContractDto.RoomTypeDto();
            roomTypeDto.setRoomType(roomType.getRoomType());
            roomTypeDto.setMaxAdults(roomType.getMaxAdults());
            roomTypeDto.setPricePerPerson(roomType.getPricePerPerson());
            roomTypeDto.setAmountOfRooms(roomType.getAmountOfRooms());
            return roomTypeDto;
        }).collect(Collectors.toList()));

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
