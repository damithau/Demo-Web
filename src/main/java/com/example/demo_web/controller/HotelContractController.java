package com.example.demo_web.controller;

import com.example.demo_web.dto.HotelContractDto;
import com.example.demo_web.exception.ResourceNotFoundException;
import com.example.demo_web.service.HotelContractService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;

import org.springframework.web.bind.annotation.*;

/**
 * REST Controller for managing hotel contracts.
 * Provides endpoints for fetching, submitting, and updating hotel contracts.
 */

@RestController
@RequestMapping("/api/v1/contract")
@Validated
public class HotelContractController {

    private final HotelContractService contractService;

    /**
     * Constructor to inject the {@link HotelContractService}.
     *
     * @param contractService Service to handle business logic for hotel contracts
     */

    @Autowired
    public HotelContractController(HotelContractService contractService) {
        this.contractService = contractService;
    }


    /**
     * Retrieves contract details by the contract ID.
     *
     * @param contractId ID of the contract to fetch
     * @return ResponseEntity containing the contract details or a not-found exception
     * @throws ResourceNotFoundException if no contract is found with the given ID
     */

    // Get contract details by contractId
    @GetMapping("/{contractId}")
    public ResponseEntity<HotelContractDto> findContractById(@PathVariable Long contractId) {
        HotelContractDto contract = contractService.getContractById(contractId);
        if (contract == null) {
            throw new ResourceNotFoundException("Contract not found with ID: " + contractId);
        }
        return ResponseEntity.ok(contract);
    }


    /**
     * Submits a new hotel contract with room details.
     *
     * @param dto Data Transfer Object containing the contract and room details
     * @return ResponseEntity with the saved contract details and HTTP status CREATED
     */
    @PostMapping("/submit")
    public ResponseEntity<HotelContractDto> submitContract(@Valid @RequestBody HotelContractDto dto) {
        return new ResponseEntity<>(contractService.saveContractWithRooms(dto), HttpStatus.CREATED);
    }

    /**
     * Updates the markup percentage for an existing hotel contract.
     *
     * @param contractId      ID of the contract to update
     * @param markupPercentage New markup percentage to apply
     * @return ResponseEntity with a success message or an appropriate exception
     * @throws ResourceNotFoundException if the contract with the given ID is not found
     * @throws IllegalArgumentException if there is an error during the update process
     */

    @PutMapping("/{contractId}/markup")
    public ResponseEntity<String> updateMarkupPercentage(
            @PathVariable Long contractId,
            @RequestParam double markupPercentage) {
        try {
            contractService.updateMarkupPercentage(contractId, markupPercentage);
            return ResponseEntity.ok("Markup percentage updated successfully");
        } catch (ResourceNotFoundException ex) {
            throw new ResourceNotFoundException("Contract with ID " + contractId + " not found");
        } catch (Exception ex) {
            throw new IllegalArgumentException("Error updating markup percentage: " + ex.getMessage());
        }
    }


}
