package com.example.demo_web.dto.request_body;


import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;


/**
 * Data Transfer Object (DTO) representing the search criteria for hotel searches.
 * Contains information about the check-in date, number of nights, and room requests.
 */
public class SearchCriteriaDto {

    @NotNull(message = "Check-in date is required")
    private LocalDate checkInDate;

    @NotNull(message = "Number of nights is required")
    private Integer noOfNights;

    @NotNull(message = "Rooms and adults details are required")
    private List<RoomRequest> roomRequests;

    public Integer getNoOfNights() {
        return noOfNights;
    }

    public void setNoOfNights(Integer noOfNights) {
        this.noOfNights = noOfNights;
    }

    public List<RoomRequest> getRoomRequests() {
        return roomRequests;
    }

    public void setRoomRequests(List<RoomRequest> roomRequests) {
        this.roomRequests = roomRequests;
    }

    public LocalDate getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(LocalDate checkInDate) {
        this.checkInDate = checkInDate;
    }


    /**
     * Nested static class representing a room request within the search criteria.
     * Contains information about the number of rooms and the number of adults.
     */


    public static class RoomRequest {
        private Integer noOfRooms;
        private Integer noOfAdults;

        public Integer getNoOfRooms() {
            return noOfRooms;
        }

        public void setNoOfRooms(Integer noOfRooms) {
            this.noOfRooms = noOfRooms;
        }

        public Integer getNoOfAdults() {
            return noOfAdults;
        }

        public void setNoOfAdults(Integer noOfAdults) {
            this.noOfAdults = noOfAdults;
        }



    }
}

