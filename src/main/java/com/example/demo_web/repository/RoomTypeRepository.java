
package com.example.demo_web.repository;

import com.example.demo_web.model.RoomType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomTypeRepository extends JpaRepository<RoomType, Long> {
    List<RoomType> findByHotelContractContractId(Long contractId);
}
