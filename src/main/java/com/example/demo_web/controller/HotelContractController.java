package com.example.demo_web.controller;

import com.example.demo_web.dto.HotelContractDto;
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
        return ResponseEntity.ok(contracts);
    }


    @PostMapping("/submit")
    public ResponseEntity<HotelContractDto> submitContract(@Valid @RequestBody HotelContractDto dto) {
        return new ResponseEntity<>(contractService.saveContractWithRooms(dto), HttpStatus.CREATED);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errors.put(error.getField(), error.getDefaultMessage());
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
    @PutMapping("/{contractId}/markup")
    public ResponseEntity<String> updateMarkupPercentage(
            @PathVariable Long contractId,
            @RequestParam double markupPercentage) {
        try {
            contractService.updateMarkupPercentage(contractId, markupPercentage);
            return ResponseEntity.ok("Markup percentage updated successfully.");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }


}
