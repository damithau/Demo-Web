package com.example.demo_web.controller;

import com.example.demo_web.dto.HotelContractDto;
import com.example.demo_web.exception.ResourceNotFoundException;
import com.example.demo_web.service.HotelContractService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;



@CrossOrigin(origins = "http://localhost:4200") // Allow Angular frontend

@RestController
@RequestMapping("/api/v1/contract")
@Validated
public class HotelContractController {

    private final HotelContractService contractService;

    @Autowired
    public HotelContractController(HotelContractService contractService) {
        this.contractService = contractService;
    }
    @GetMapping
    public ResponseEntity<List<HotelContractDto>> getContractsWithRoomTypes() {
        List<HotelContractDto> contracts = contractService.getContractsWithRoomTypes();
        if (contracts.isEmpty()) {
            throw new ResourceNotFoundException("No contracts found");
        }
        return ResponseEntity.ok(contracts);

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
