package com.example.demo_web.service;

import com.example.demo_web.dto.request_body.SearchCriteriaDto;
import com.example.demo_web.dto.response_body.SearchResultDto;
import com.example.demo_web.model.HotelContract;
import com.example.demo_web.model.RoomType;
import com.example.demo_web.repository.HotelContractRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class HotelSearchService {

    private final HotelContractRepository contractRepository;

    public HotelSearchService(HotelContractRepository contractRepository) {
        this.contractRepository = contractRepository;
    }

//    public List<SearchResultDto> searchHotels(SearchCriteriaDto criteria) {
//        LocalDate checkOutDate = criteria.getCheckInDate().plusDays(criteria.getNoOfNights());
//
//        List<SearchResultDto> results = new ArrayList<>();
//
//        // Query contracts that satisfy date criteria
//        List<HotelContract> contracts = contractRepository.findValidContracts(
//                criteria.getCheckInDate(),
//                checkOutDate
//        );
//
//        for (HotelContract contract : contracts) {
//            SearchResultDto result = new SearchResultDto();
//            result.setHotelName(contract.getHotelName());
//
//            List<SearchResultDto.RoomTypeResult> roomTypeResults = new ArrayList<>();
//
//            for (RoomType roomType : contract.getRoomTypes()) {
//                // Check if room availability and adults requirement are satisfied
//                boolean isAvailable = criteria.getRoomRequests().stream().allMatch(request ->
//                        roomType.getAmountOfRooms() >= request.getNoOfRooms() &&
//                                roomType.getMaxAdults() == request.getNoOfAdults()
//                );
//
//                if (isAvailable) {
//                    SearchResultDto.RoomTypeResult roomTypeResult = new SearchResultDto.RoomTypeResult();
//                    roomTypeResult.setRoomType(roomType.getRoomType());
//                    roomTypeResult.setMarkedUpPrice(
//                            (double) (roomType.getPricePerPerson() *
//                                                                (1 + contract.getMarkupPercentage() / 100) *
//                                                                criteria.getNoOfNights() *
//                                                                criteria.getRoomRequests().stream().mapToInt(SearchCriteriaDto.RoomRequest::getNoOfAdults).sum())
//                    );
//                    roomTypeResults.add(roomTypeResult);
//                }
//            }
//
//            result.setRoomTypes(roomTypeResults);
//            if (!roomTypeResults.isEmpty()) {
//                results.add(result);
//            }
//        }
//
//        return results;
//    }
public List<SearchResultDto> searchHotels(SearchCriteriaDto criteria) {
    LocalDate checkOutDate = criteria.getCheckInDate().plusDays(criteria.getNoOfNights());
    List<SearchResultDto> results = new ArrayList<>();

    // Query contracts that satisfy the date criteria
    List<HotelContract> contracts = contractRepository.findValidContracts(
            criteria.getCheckInDate(),
            checkOutDate
    );

    for (HotelContract contract : contracts) {
        // Map to track remaining rooms for each room type
        Map<String, Integer> availableRooms = new HashMap<>();
        for (RoomType roomType : contract.getRoomTypes()) {
            availableRooms.put(roomType.getRoomType(), roomType.getAmountOfRooms());
        }

        boolean allRequestsFulfilled = true;
        List<SearchResultDto.RoomTypeResult> roomTypeResults = new ArrayList<>();

        // Iterate over room requests
        for (SearchCriteriaDto.RoomRequest request : criteria.getRoomRequests()) {
            boolean requestFulfilled = false;

            // Try to find a room type in the current hotel that can fulfill the request
            for (RoomType roomType : contract.getRoomTypes()) {
                if (roomType.getMaxAdults() == request.getNoOfAdults() &&
                        availableRooms.get(roomType.getRoomType()) != null &&
                        availableRooms.get(roomType.getRoomType()) >= request.getNoOfRooms()) {

                    // Fulfill the request
                    availableRooms.put(roomType.getRoomType(),
                            availableRooms.get(roomType.getRoomType()) - request.getNoOfRooms());

                    // Calculate the marked-up price
                    double markedUpPrice = roomType.getPricePerPerson() *
                            (1 + contract.getMarkupPercentage() / 100) *
                            criteria.getNoOfNights() *
                            request.getNoOfAdults();

                    // Add the room type to the result
                    SearchResultDto.RoomTypeResult roomTypeResult = new SearchResultDto.RoomTypeResult();
                    roomTypeResult.setRoomType(roomType.getRoomType());
                    roomTypeResult.setMarkedUpPrice(markedUpPrice);
                    roomTypeResults.add(roomTypeResult);

                    requestFulfilled = true;
                    break;
                }
            }

            // If any request is not fulfilled, mark the hotel as unavailable
            if (!requestFulfilled) {
                allRequestsFulfilled = false;
                break;
            }
        }

        // Add the hotel to results only if all requests are fulfilled
        if (allRequestsFulfilled) {
            SearchResultDto result = new SearchResultDto();
            result.setHotelName(contract.getHotelName());
            result.setRoomTypes(roomTypeResults);
            results.add(result);
        }
    }

    return results;
}



}

