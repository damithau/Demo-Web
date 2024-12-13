package com.example.demo_web.controller;



import com.example.demo_web.dto.HotelContractDto;
import com.example.demo_web.exception.ResourceNotFoundException;
import com.example.demo_web.service.HotelContractService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class HotelContractControllerTest {

    @Mock
    private HotelContractService contractService;

    @InjectMocks
    private HotelContractController contractController;

    public HotelContractControllerTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindContractById_ContractFound() {
        // Arrange
        Long contractId = 1L;
        HotelContractDto dto = new HotelContractDto();  // Populate as needed
        when(contractService.getContractById(contractId)).thenReturn(dto);

        // Act
        ResponseEntity<HotelContractDto> response = contractController.findContractById(contractId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(dto, response.getBody());
        verify(contractService, times(1)).getContractById(contractId);
    }

    @Test
    void testFindContractById_ContractNotFound() {
        // Arrange
        Long contractId = 1L;
        when(contractService.getContractById(contractId)).thenReturn(null);

        // Act & Assert
        try {
            contractController.findContractById(contractId);
        } catch (ResourceNotFoundException ex) {
            assertEquals("Contract not found with ID: " + contractId, ex.getMessage());
        }
        verify(contractService, times(1)).getContractById(contractId);
    }

    @Test
    void testSubmitContract_ValidDto() {
        // Arrange
        HotelContractDto dto = new HotelContractDto();  // Populate as needed
        when(contractService.saveContractWithRooms(dto)).thenReturn(dto);

        // Act
        ResponseEntity<HotelContractDto> response = contractController.submitContract(dto);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(dto, response.getBody());
        verify(contractService, times(1)).saveContractWithRooms(dto);
    }

    @Test
    void testUpdateMarkupPercentage_Success() {
        // Arrange
        Long contractId = 1L;
        double markupPercentage = 10.0;

        // Act
        ResponseEntity<String> response = contractController.updateMarkupPercentage(contractId, markupPercentage);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Markup percentage updated successfully", response.getBody());
        verify(contractService, times(1)).updateMarkupPercentage(contractId, markupPercentage);
    }
}
