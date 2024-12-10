package com.example.demo_web.dto.response_body;


import java.util.List;

public class SearchResultDto {
    private String hotelName;
    private List<RoomTypeResult> roomTypes;

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public List<RoomTypeResult> getRoomTypes() {
        return roomTypes;
    }

    public void setRoomTypes(List<RoomTypeResult> roomTypes) {
        this.roomTypes = roomTypes;
    }



    public static class RoomTypeResult {
        private String roomType;
        private Double markedUpPrice;

        public Double getMarkedUpPrice() {
            return markedUpPrice;
        }

        public void setMarkedUpPrice(Double markedUpPrice) {
            this.markedUpPrice = markedUpPrice;
        }

        public String getRoomType() {
            return roomType;
        }

        public void setRoomType(String roomType) {
            this.roomType = roomType;
        }




    }


}

