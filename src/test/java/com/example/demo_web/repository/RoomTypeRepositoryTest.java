
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

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback
class RoomTypeRepositoryTest {

    @Autowired
    private RoomTypeRepository roomTypeRepository;

    @Autowired
    private HotelContractRepository hotelContractRepository;

    @Test
    void testFindByHotelContractContractId() {
        // Prepare test data
        HotelContract contract = new HotelContract();
        contract.setHotelName("Test Hotel");
        contract.setContractValidFrom(LocalDate.of(2024, 1, 1));
        contract.setContractValidTo(LocalDate.of(2024, 12, 31));
        contract.setMarkupPercentage(10.0F);

        HotelContract savedContract = hotelContractRepository.save(contract);

        RoomType roomType = new RoomType();
        roomType.setRoomType("Deluxe");
        roomType.setHotelContract(savedContract);
        roomTypeRepository.save(roomType);

        // Query
        List<RoomType> results = roomTypeRepository.findByHotelContractContractId(savedContract.getContractId());
        assertFalse(results.isEmpty());
        assertEquals("Deluxe", results.get(0).getRoomType());
    }
}
