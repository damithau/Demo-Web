
package com.example.demo_web.service;

import com.example.demo_web.dto.request_body.SearchCriteriaDto;
import com.example.demo_web.dto.response_body.SearchResultDto;
import com.example.demo_web.model.HotelContract;
import com.example.demo_web.repository.HotelContractRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
        import static org.mockito.Mockito.*;

class HotelSearchServiceTest {

    @Mock
    private HotelContractRepository contractRepository;

    @InjectMocks
    private HotelSearchService searchService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSearchHotels_ValidCriteria() {
        SearchCriteriaDto criteria = new SearchCriteriaDto();
        criteria.setCheckInDate(LocalDate.now());
        criteria.setNoOfNights(3);

        List<HotelContract> mockContracts = new ArrayList<>();
        when(contractRepository.findValidContracts(any(), any())).thenReturn(mockContracts);

        List<SearchResultDto> results = searchService.searchHotels(criteria);

        assertNotNull(results);
        verify(contractRepository, times(1)).findValidContracts(any(), any());
    }

    @Test
    void testSearchHotels_NoResults() {
        SearchCriteriaDto criteria = new SearchCriteriaDto();
        criteria.setCheckInDate(LocalDate.now());
        criteria.setNoOfNights(3);

        when(contractRepository.findValidContracts(any(), any())).thenReturn(new ArrayList<>());

        List<SearchResultDto> results = searchService.searchHotels(criteria);

        assertTrue(results.isEmpty());
        verify(contractRepository, times(1)).findValidContracts(any(), any());
    }
}
