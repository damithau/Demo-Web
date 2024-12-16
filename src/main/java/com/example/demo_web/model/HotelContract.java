package com.example.demo_web.model;

import jakarta.persistence.*;


import java.time.LocalDate;
import java.util.List;

/**
 * Entity representing a hotel contract. This class maps to the `hotel_contract` table in the database.
 * It contains details about the hotel, contract validity dates, markup percentage, and associated room types.
 */
@Entity
public class HotelContract {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long contractId;
    @Column(nullable = false)
    private String hotelName;
    @Column(nullable = false)
    private LocalDate contractValidFrom;
    @Column(nullable = false)
    private LocalDate contractValidTo;
    @Column(nullable = false)
    private Float markupPercentage;


    /**
     * The list of room types associated with the hotel contract.
     * This is a one-to-many relationship with the `RoomType` entity.
     * The `hotelContract` field in the `RoomType` entity maps back to this field.
     */
    @OneToMany(mappedBy = "hotelContract", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RoomType> roomTypes;

    // Getters and Setters
    /**
     * Gets the start date of the contract validity.
     * @return The contract validity start date.
     */
    public LocalDate getContractValidFrom() {
        return contractValidFrom;
    }

    /**
     * Sets the start date of the contract validity.
     * @param contractValidFrom The contract validity start date to set.
     *Like wise for all other functions*/
    public void setContractValidFrom(LocalDate contractValidFrom) {
        this.contractValidFrom = contractValidFrom;
    }

    public LocalDate getContractValidTo() {
        return contractValidTo;
    }

    public void setContractValidTo(LocalDate contractValidTo) {
        this.contractValidTo = contractValidTo;
    }

    public Float getMarkupPercentage() {
        return markupPercentage;
    }

    public void setMarkupPercentage(Float markupPercentage) {
        this.markupPercentage = markupPercentage;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public Long getContractId() {
        return contractId;
    }

    public void setContractId(Long contractId) {
        this.contractId = contractId;
    }

    public List<RoomType> getRoomTypes() {
        return roomTypes;
    }

    public void setRoomTypes(List<RoomType> roomTypes) {
        this.roomTypes = roomTypes;
    }

    // Constructors

    public HotelContract() {
    }

    public HotelContract(Long contractId, String hotelName, LocalDate contractValidFrom, LocalDate contractValidTo, Float markupPercentage) {
        this.contractId = contractId;
        this.hotelName = hotelName;
        this.contractValidFrom = contractValidFrom;
        this.contractValidTo = contractValidTo;
        this.markupPercentage = markupPercentage;
    }
}
