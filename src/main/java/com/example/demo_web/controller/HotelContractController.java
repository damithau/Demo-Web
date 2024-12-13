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



@RestController
@RequestMapping("/api/v1/contract")
@Validated
public class HotelContractController {

    private final HotelContractService contractService;

    @Autowired
    public HotelContractController(HotelContractService contractService) {
        this.contractService = contractService;
    }


    // Get contract details by contractId
    @GetMapping("/{contractId}")
    public ResponseEntity<HotelContractDto> findContractById(@PathVariable Long contractId) {
        HotelContractDto contract = contractService.getContractById(contractId);
        if (contract == null) {
            throw new ResourceNotFoundException("Contract not found with ID: " + contractId);
        }
        return ResponseEntity.ok(contract);
    }


    @PostMapping("/submit")
    public ResponseEntity<HotelContractDto> submitContract(@Valid @RequestBody HotelContractDto dto) {
        return new ResponseEntity<>(contractService.saveContractWithRooms(dto), HttpStatus.CREATED);
    }

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
