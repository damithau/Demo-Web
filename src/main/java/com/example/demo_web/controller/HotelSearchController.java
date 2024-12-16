package com.example.demo_web.controller;


import com.example.demo_web.dto.request_body.SearchCriteriaDto;
import com.example.demo_web.dto.response_body.SearchResultDto;
import com.example.demo_web.service.HotelSearchService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * REST Controller for handling hotel search operations.
 * Provides an endpoint to search hotels based on given criteria.
 */
@RestController
@RequestMapping("/api/v1/hotels")
@Validated
public class HotelSearchController {



    private final HotelSearchService searchService;

    /**
     * Constructor to inject the {@link HotelSearchService}.
     *
     * @param searchService Service to handle business logic for hotel search
     */

    public HotelSearchController(HotelSearchService searchService) {
        this.searchService = searchService;
    }


    /**
     * Searches for hotels based on the given search criteria.
     *
     * @param criteria Data Transfer Object containing search parameters such as check-in date,
     *                 number of nights, and room requests
     * @return ResponseEntity containing a list of search results matching the criteria
     */
    @PostMapping ("/search")
    public ResponseEntity<List<SearchResultDto>> searchHotels(@Valid @RequestBody SearchCriteriaDto criteria) {
        List<SearchResultDto> results = searchService.searchHotels(criteria);
        return ResponseEntity.ok(results);
    }
}
