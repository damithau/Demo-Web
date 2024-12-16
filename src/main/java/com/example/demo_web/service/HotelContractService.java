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


/**
 * Service class responsible for managing hotel contracts and their associated operations.
 * This service handles fetching, adding, deleting, and updating hotel contracts.
 */
@Service
public class HotelContractService
{

    public static final Logger logger = LoggerFactory.getLogger( HotelContractService.class );

    private final HotelContractRepository contractRepository;

    /**
     * Constructor for the HotelContractService class.
     *
     * @param contractRepository The repository for accessing hotel contract data.
     */
    @Autowired
    public HotelContractService(HotelContractRepository contractRepository)
    {
        this.contractRepository = contractRepository;
    }

    /**
     * Fetches a hotel contract by its ID.
     * If the contract is found, it is mapped to a DTO and returned.
     * If not found, throws a {@link ContractNotFoundException}.
     *
     * @param contractId The ID of the contract to fetch.
     * @return A DTO representing the hotel contract.
     * @throws ContractNotFoundException if no contract with the given ID is found.
     */
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

    /**
     * Fetches all hotel contracts and maps them to DTOs.
     *
     * @return A list of DTOs representing all hotel contracts.
     */
    public List<HotelContractDto> getContracts()
    {
        logger.info( "Getting all contracts" );
        List<HotelContract> contracts = contractRepository.findAll();


        return contracts.stream().map( contract -> {return ContractMapper.entityToDto( contract );} ).toList();
    }
    /**
     * Adds a new hotel contract.
     * The contract is saved using the {@link HotelContractRepository} and the saved contract is returned as a DTO.
     *
     * @param dto The DTO representing the hotel contract to add.
     * @return The saved contract represented as a DTO.
     */
    public HotelContractDto addContract( HotelContractDto dto )
    {
        HotelContract contract = ContractMapper.dtoToEntity( dto );
        HotelContract saved = contractRepository.save( contract );
        return ContractMapper.entityToDto( saved );
    }


    /**
     * Deletes a hotel contract by its ID.
     * If the contract with the given ID does not exist, a {@link ContractNotFoundException} is thrown.
     *
     * @param contractId The ID of the contract to delete.
     * @throws ContractNotFoundException if no contract with the given ID exists.
     */
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

    /**
     * Saves a hotel contract along with its associated room types.
     * The room types are mapped from the provided DTO and linked to the contract.
     *
     * @param dto The DTO representing the hotel contract with room types.
     * @return The saved contract and its room types as a DTO.
     */
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

    /**
     * Updates the markup percentage of a hotel contract.
     * This operation is transactional, ensuring the update occurs within a single transaction.
     *
     * @param contractId The ID of the contract to update.
     * @param newMarkupPercentage The new markup percentage to set for the contract.
     * @throws IllegalArgumentException if the new markup percentage is negative.
     * @throws EntityNotFoundException if no contract with the given ID exists.
     */
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

    /**
     * Fetches all hotel contracts with their associated room types and returns them as DTOs.
     *
     * @return A list of hotel contract DTOs, each containing associated room types.
     */


    public List<HotelContractDto> getContractsWithRoomTypes() {
        List<HotelContract> contracts = contractRepository.findAll();
        return contracts.stream()
                .map(ContractMapper::entityToDto)
                .collect(Collectors.toList());
    }




}
