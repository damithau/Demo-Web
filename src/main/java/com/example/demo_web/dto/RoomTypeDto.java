package com.example.demo_web.dto;


import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * Data Transfer Object (DTO) representing details of a room type.
 */
public class RoomTypeDto {
    @NotNull(message = "Room type name is required")
    @Size(min = 3, max = 20, message = "Room type must be between 3 and 20 characters")
    private String roomType;
    @NotNull(message = "Price per person is required")
    @DecimalMin(value = "0.00", inclusive = false, message = "Price per person must be greater than 0")
    private Float pricePerPerson;
    @NotNull(message = "Number of rooms is required")
    @Min(value = 1, message = "Number of rooms must be at least 1")
    private Integer noOfRooms;
    @NotNull(message = "Maximum adults is required")
    @Min(value = 1, message = "Maximum adults must be at least 1")
    private Integer maxAdults;

    // Getters and Setters

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public Float getPricePerPerson() {
        return pricePerPerson;
    }

    public void setPricePerPerson(Float pricePerPerson) {
        this.pricePerPerson = pricePerPerson;
    }

    public Integer getNoOfRooms() {
        return noOfRooms;
    }

    public void setNoOfRooms(Integer noOfRooms) {
        this.noOfRooms = noOfRooms;
    }

    public Integer getMaxAdults() {
        return maxAdults;
    }

    public void setMaxAdults(Integer maxAdults) {
        this.maxAdults = maxAdults;
    }
}
