package com.example.demo_web.service;

import com.example.demo_web.dto.HotelContractDto;
import com.example.demo_web.exception.ContractNotFoundException;
import com.example.demo_web.model.HotelContract;
import com.example.demo_web.model.RoomType;
import com.example.demo_web.repository.HotelContractRepository;
import com.example.demo_web.utils.ContractMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class HotelContractService
{

    public static final Logger logger = LoggerFactory.getLogger( HotelContractService.class );

    private final HotelContractRepository contractRepository;

    @Autowired
    public HotelContractService(HotelContractRepository contractRepository)
    {
        this.contractRepository = contractRepository;
    }

    public List<HotelContractDto> getContracts()
    {
        logger.info( "Getting all contracts" );
        List<HotelContract> contracts = contractRepository.findAll();


        return contracts.stream().map( contract -> {return ContractMapper.entityToDto( contract );} ).toList();
    }

    public HotelContractDto addContract( HotelContractDto dto )
    {
        HotelContract contract = ContractMapper.dtoToEntity( dto );
        HotelContract saved = contractRepository.save( contract );
        return ContractMapper.entityToDto( saved );
    }

    public void deleteContract( Long contractId )
    {
//        contractRepository.deleteById( contractId );
        Optional<HotelContract> contractOptional = contractRepository.findById( contractId );
        if(contractOptional.isPresent())
        {
            contractRepository.deleteById( contractId );
        }
        else
        {
            throw new ContractNotFoundException( "Contract with Id" + contractId + " was not found" );
        }
    }
    public HotelContractDto saveContractWithRooms(HotelContractDto dto) {
        // Convert DTO to Entity
        HotelContract contract = ContractMapper.dtoToEntity(dto);

        // Map Room Types
        List<RoomType> roomTypes = dto.getRoomTypes().stream().map(roomDto -> {
            RoomType room = new RoomType();
            room.setRoomType(roomDto.getRoomType());
            room.setPricePerPerson(roomDto.getPricePerPerson());
            room.setAmountOfRooms(roomDto.getNoOfRooms());
            room.setMaxAdults(roomDto.getMaxAdults());
            room.setHotelContract(contract); // Set Parent
            return room;
        }).toList();

        contract.setRoomTypes(roomTypes);

        // Save Contract and Room Types
        HotelContract savedContract = contractRepository.save(contract);

        // Convert Back to DTO
        return ContractMapper.entityToDto(savedContract);
    }

}
