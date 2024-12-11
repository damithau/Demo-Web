
package com.example.demo_web.dto;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


import java.time.LocalDate;
import java.util.List;


public class HotelContractDto {
    private Long contractId;
    @NotNull(message = "Hotel name is required")
    @Size(min = 2, max = 50, message = "Hotel name must be between 2 and 50 characters")
    private String hotelName;
    @NotNull(message = "Contract valid from date is required")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate contractValidFrom;
    @NotNull(message = "Contract valid to date is required")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate contractValidTo;
    @NotNull(message = "Markup percentage is required")
    @Min(value = 0, message = "Markup percentage must be at least 0")
    @Max(value = 100, message = "Markup percentage cannot exceed 100")
    private Float markupPercentage;
    @NotNull(message = "Room types cannot be null")
    @Size(min = 1, message = "At least one room type must be provided")
    private List<RoomTypeDto> roomTypes;

    // Getters and Setters
    public Long getContractId() {
        return contractId;
    }

    public void setContractId(Long contractId) {
        this.contractId = contractId;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

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

    public List<RoomTypeDto> getRoomTypes() {
        return roomTypes;
    }

    public void setRoomTypes(List<RoomTypeDto> roomTypes) {
        this.roomTypes = roomTypes;
    }
    public static class RoomTypeDto {
        private String roomType;
        private int maxAdults;
        private double pricePerPerson;
        private int amountOfRooms;

        public float getPricePerPerson() {
            return (float) pricePerPerson;
        }

        public void setPricePerPerson(double pricePerPerson) {
            this.pricePerPerson = pricePerPerson;
        }

        public String getRoomType() {
            return roomType;
        }

        public void setRoomType(String roomType) {
            this.roomType = roomType;
        }

        public int getMaxAdults() {
            return maxAdults;
        }

        public void setMaxAdults(int maxAdults) {
            this.maxAdults = maxAdults;
        }

        public int getAmountOfRooms() {
            return amountOfRooms;
        }

        public void setAmountOfRooms(int amountOfRooms) {
            this.amountOfRooms = amountOfRooms;
        }
    }


}
