
package com.example.demo_web.model;

import jakarta.persistence.*;



/**
 * Entity representing a room type in a hotel contract. This class maps to the `room_type` table in the database.
 * It contains details about the type of room, its price, the number of rooms available, and the maximum number of adults allowed.
 */
@Entity
public class RoomType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roomTypeId;

    @ManyToOne
    @JoinColumn(name = "contract_id", nullable = false)
    private HotelContract hotelContract;

    private String roomType;
    private Float pricePerPerson;
    private Integer amountOfRooms;
    private Integer maxAdults;

    // Getters and Setters

    public Long getRoomTypeId() {
        return roomTypeId;
    }

    public void setRoomTypeId(Long roomTypeId) {
        this.roomTypeId = roomTypeId;
    }

    public HotelContract getHotelContract() {
        return hotelContract;
    }

    public void setHotelContract(HotelContract hotelContract) {
        this.hotelContract = hotelContract;
    }

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

    public Integer getAmountOfRooms() {
        return amountOfRooms;
    }

    public void setAmountOfRooms(Integer amountOfRooms) {
        this.amountOfRooms = amountOfRooms;
    }

    public Integer getMaxAdults() {
        return maxAdults;
    }

    public void setMaxAdults(Integer maxAdults) {
        this.maxAdults = maxAdults;
    }
}
