package com.example.demo_web.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
public class HotelContract {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long contractId;

    private String hotelName;

    private LocalDate contractValidFrom;

    private LocalDate contractValidTo;

    private Float markupPercentage;

    @OneToMany(mappedBy = "hotelContract", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RoomType> roomTypes;

    // Getters and Setters

    public LocalDate getContractValidFrom() {
        return contractValidFrom;
    }

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
