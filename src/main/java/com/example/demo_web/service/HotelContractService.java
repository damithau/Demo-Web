package com.example.demo_web.service;

import com.example.demo_web.dto.HotelContractDto;
import com.example.demo_web.exception.ContractNotFoundException;
import com.example.demo_web.model.HotelContract;
import com.example.demo_web.model.RoomType;
import com.example.demo_web.repository.HotelContractRepository;
import com.example.demo_web.utils.ContractMapper;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    // Fetch contract by ID
    public HotelContractDto getContractById(Long contractId) {
        Optional<HotelContract> contractOptional = contractRepository.findById(contractId);
        if (contractOptional.isPresent()) {
            HotelContract contract = contractOptional.get();
            return ContractMapper.entityToDto(contract);
        } else {
            throw new ContractNotFoundException("Contract with ID " + contractId + " not found.");
        }
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
            room.setAmountOfRooms(roomDto.getAmountOfRooms());
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
    @Transactional
    public void updateMarkupPercentage(Long contractId, double newMarkupPercentage) {
        if (newMarkupPercentage < 0) {
            throw new IllegalArgumentException("Markup percentage must be positive.");
        }

        int rowsUpdated = contractRepository.updateMarkupPercentage(contractId, newMarkupPercentage);

        if (rowsUpdated == 0) {
            throw new EntityNotFoundException("No contract found with ID: " + contractId);
        }
    }


    public List<HotelContractDto> getContractsWithRoomTypes() {
        List<HotelContract> contracts = contractRepository.findAll();
        return contracts.stream()
                .map(ContractMapper::entityToDto)
                .collect(Collectors.toList());
    }




}
