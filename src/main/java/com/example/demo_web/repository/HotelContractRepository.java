package com.example.demo_web.repository;

import com.example.demo_web.model.HotelContract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface HotelContractRepository extends JpaRepository<HotelContract, Long>
{
    @Query("SELECT c FROM HotelContract c WHERE :checkInDate BETWEEN c.contractValidFrom AND c.contractValidTo AND :checkOutDate BETWEEN c.contractValidFrom AND c.contractValidTo")
    List<HotelContract> findValidContracts(@Param("checkInDate") LocalDate checkInDate, @Param("checkOutDate") LocalDate checkOutDate);

    @Modifying
    @Query("UPDATE HotelContract hc SET hc.markupPercentage = :markupPercentage WHERE hc.id = :contractId")
    int updateMarkupPercentage(@Param("contractId") Long contractId, @Param("markupPercentage") double markupPercentage);

    @Query("SELECT c FROM HotelContract c JOIN FETCH c.roomTypes WHERE c.id = :contractId")
    List<HotelContract> findContractsWithRoomTypes(@Param("contractId") Long contractId);


}
