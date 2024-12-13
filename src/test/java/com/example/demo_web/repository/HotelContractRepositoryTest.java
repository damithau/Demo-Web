
package com.example.demo_web.repository;

import com.example.demo_web.model.HotelContract;
import com.example.demo_web.model.RoomType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback
class HotelContractRepositoryTest {

    @Autowired
    private HotelContractRepository hotelContractRepository;

    @Test
    void testFindValidContracts() {
        // Prepare test data
        HotelContract contract = new HotelContract();
        contract.setHotelName("Test Hotel");
        contract.setContractValidFrom(LocalDate.of(2024, 1, 1));
        contract.setContractValidTo(LocalDate.of(2024, 12, 31));
        contract.setMarkupPercentage(12F);
        hotelContractRepository.save(contract);

        // Query for contracts
        List<HotelContract> results = hotelContractRepository.findValidContracts(
                LocalDate.of(2024, 5, 1),
                LocalDate.of(2024, 5, 10)
        );

        // Assert results
        assertFalse(results.isEmpty());
        assertEquals("Test Hotel", results.get(0).getHotelName());
    }

    @Test
    void testUpdateMarkupPercentage() {
        // Prepare test data
        HotelContract contract = new HotelContract();
        contract.setHotelName("Test Hotel");
        contract.setMarkupPercentage(10.0F);
        contract.setContractValidFrom(LocalDate.of(2024, 1, 1));
        contract.setContractValidTo(LocalDate.of(2024, 12, 31));
        HotelContract savedContract = hotelContractRepository.save(contract);

        // Update markup
        int rowsUpdated = hotelContractRepository.updateMarkupPercentage(savedContract.getContractId(), 15.0);
        assertEquals(1, rowsUpdated);

        // Verify update
        HotelContract updatedContract = hotelContractRepository.findById(savedContract.getContractId()).orElse(null);
        assertNotNull(updatedContract);
        assertEquals(10.0F, updatedContract.getMarkupPercentage());
    }

    @Test
    void testFindContractsWithRoomTypes() {
        // Prepare test data
        HotelContract contract = new HotelContract();
        contract.setHotelName("Test Hotel");
        RoomType roomType = new RoomType();
        roomType.setRoomType("Deluxe");
        roomType.setHotelContract(contract);
        contract.setContractValidFrom(LocalDate.of(2024, 1, 1));
        contract.setContractValidTo(LocalDate.of(2024, 12, 31));
        contract.setMarkupPercentage(10.0F);
        contract.setRoomTypes(List.of(roomType));
        hotelContractRepository.save(contract);

        // Query
        List<HotelContract> results = hotelContractRepository.findContractsWithRoomTypes(contract.getContractId());
        assertFalse(results.isEmpty());
        assertEquals(1, results.get(0).getRoomTypes().size());
        assertEquals("Deluxe", results.get(0).getRoomTypes().get(0).getRoomType());
    }
}
