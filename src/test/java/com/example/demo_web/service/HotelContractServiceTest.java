
package com.example.demo_web.service;

import com.example.demo_web.dto.HotelContractDto;
import com.example.demo_web.exception.ContractNotFoundException;
import com.example.demo_web.model.HotelContract;
import com.example.demo_web.repository.HotelContractRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
        import static org.mockito.Mockito.*;

class HotelContractServiceTest {

    @Mock
    private HotelContractRepository contractRepository;

    @InjectMocks
    private HotelContractService contractService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

//    @Test
//    void testGetContractById_ContractFound() {
//        HotelContract mockContract = new HotelContract();
//        mockContract.setContractId(1L);
//        when(contractRepository.findById(1L)).thenReturn(Optional.of(mockContract));
//
//        HotelContractDto result = contractService.getContractById(1L);
//
//        assertNotNull(result);
//        verify(contractRepository, times(1)).findById(1L);
//    }

    @Test
    void testGetContractById_ContractNotFound() {
        when(contractRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ContractNotFoundException.class, () -> contractService.getContractById(1L));
        verify(contractRepository, times(1)).findById(1L);
    }

    @Test
    void testAddContract() {
        // Mock the DTO input
        HotelContractDto dto = new HotelContractDto();
        dto.setRoomTypes(new ArrayList<>()); // Ensure roomTypes is not null

        // Mock the repository behavior
        HotelContract mockContract = new HotelContract();
        mockContract.setRoomTypes(new ArrayList<>()); // Initialize roomTypes to avoid NullPointerException
        when(contractRepository.save(any(HotelContract.class))).thenReturn(mockContract);

        // Execute the service method
        HotelContractDto result = contractService.addContract(dto);

        // Verify and assert
        assertNotNull(result);
        verify(contractRepository, times(1)).save(any(HotelContract.class));
    }


//    @Test
//    void testDeleteContract_ContractExists() {
//        when(contractRepository.findById(1L)).thenReturn(Optional.of(new HotelContract()));
//
//        assertDoesNotThrow(() -> contractService.deleteContract(1L));
//        verify(contractRepository, times(1)).deleteById(1L);
//    }

//    @Test
//    void testDeleteContract_ContractDoesNotExist() {
//        when(contractRepository.findById(1L)).thenReturn(Optional.empty());
//
//        assertThrows(ContractNotFoundException.class, () -> contractService.deleteContract(1L));
//        verify(contractRepository, times(1)).findById(1L);
//    }

    @Test
    void testUpdateMarkupPercentage_Success() {
        when(contractRepository.updateMarkupPercentage(1L, 10.0)).thenReturn(1);

        assertDoesNotThrow(() -> contractService.updateMarkupPercentage(1L, 10.0));
        verify(contractRepository, times(1)).updateMarkupPercentage(1L, 10.0);
    }

    @Test
    void testUpdateMarkupPercentage_Failure() {
        when(contractRepository.updateMarkupPercentage(1L, 10.0)).thenReturn(0);

        assertThrows(EntityNotFoundException.class, () -> contractService.updateMarkupPercentage(1L, 10.0));
        verify(contractRepository, times(1)).updateMarkupPercentage(1L, 10.0);
    }
}
