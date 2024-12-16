package com.example.demo_web.repository;

import com.example.demo_web.model.HotelContract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;


/**
 * Repository interface for accessing and managing {@link HotelContract} entities in the database.
 * This interface extends {@link JpaRepository}, providing CRUD operations and custom queries for hotel contracts.
 */
@Repository
public interface HotelContractRepository extends JpaRepository<HotelContract, Long>
{
    /**
     * Finds hotel contracts that are valid during the given check-in and check-out dates.
     * A contract is considered valid if the check-in and check-out dates both fall within the contract's validity period.
     *
     * @param checkInDate The check-in date to check.
     * @param checkOutDate The check-out date to check.
     * @return A list of valid {@link HotelContract} entities.
     */
    @Query("SELECT c FROM HotelContract c WHERE :checkInDate BETWEEN c.contractValidFrom AND c.contractValidTo AND :checkOutDate BETWEEN c.contractValidFrom AND c.contractValidTo")
    List<HotelContract> findValidContracts(@Param("checkInDate") LocalDate checkInDate, @Param("checkOutDate") LocalDate checkOutDate);


    /**
     * Updates the markup percentage for a specific hotel contract.
     * This method modifies the markup percentage of a contract identified by its ID.
     *
     * @param contractId The ID of the hotel contract to update.
     * @param markupPercentage The new markup percentage to set.
     * @return The number of rows affected by the update query.
     */
    @Modifying
    @Query("UPDATE HotelContract hc SET hc.markupPercentage = :markupPercentage WHERE hc.id = :contractId")
    int updateMarkupPercentage(@Param("contractId") Long contractId, @Param("markupPercentage") double markupPercentage);


    /**
     * Finds a hotel contract along with its associated room types.
     * This method uses a join fetch to load both the contract and its related room types in one query.
     *
     * @param contractId The ID of the hotel contract to retrieve.
     * @return A list of hotel contracts with their associated room types.
     */
    @Query("SELECT c FROM HotelContract c JOIN FETCH c.roomTypes WHERE c.id = :contractId")
    List<HotelContract> findContractsWithRoomTypes(@Param("contractId") Long contractId);


}
