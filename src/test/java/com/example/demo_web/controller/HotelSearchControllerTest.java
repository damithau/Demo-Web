
package com.example.demo_web.controller;

import com.example.demo_web.dto.request_body.SearchCriteriaDto;
import com.example.demo_web.dto.response_body.SearchResultDto;
import com.example.demo_web.service.HotelSearchService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class HotelSearchControllerTest {

    @Mock
    private HotelSearchService searchService;

    @InjectMocks
    private HotelSearchController searchController;

    public HotelSearchControllerTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSearchHotels_ValidCriteria() {
        // Arrange
        SearchCriteriaDto criteria = new SearchCriteriaDto();  // Populate as needed
        List<SearchResultDto> results = Collections.singletonList(new SearchResultDto());  // Populate as needed
        when(searchService.searchHotels(criteria)).thenReturn(results);

        // Act
        ResponseEntity<List<SearchResultDto>> response = searchController.searchHotels(criteria);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(results, response.getBody());
        verify(searchService, times(1)).searchHotels(criteria);
    }

    @Test
    void testSearchHotels_NoResults() {
        // Arrange
        SearchCriteriaDto criteria = new SearchCriteriaDto();  // Populate as needed
        when(searchService.searchHotels(criteria)).thenReturn(Collections.emptyList());

        // Act
        ResponseEntity<List<SearchResultDto>> response = searchController.searchHotels(criteria);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(0, response.getBody().size());
        verify(searchService, times(1)).searchHotels(criteria);
    }
}
