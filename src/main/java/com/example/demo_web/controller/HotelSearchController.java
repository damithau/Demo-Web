package com.example.demo_web.controller;


import com.example.demo_web.dto.request_body.SearchCriteriaDto;
import com.example.demo_web.dto.response_body.SearchResultDto;
import com.example.demo_web.service.HotelSearchService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/hotels")
@Validated
public class HotelSearchController {

    private final HotelSearchService searchService;

    public HotelSearchController(HotelSearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping ("/search")
    public ResponseEntity<List<SearchResultDto>> searchHotels(@Valid @RequestBody SearchCriteriaDto criteria) {
        List<SearchResultDto> results = searchService.searchHotels(criteria);
        return ResponseEntity.ok(results);
    }
}
