package com.inditex.project.infrastructure.service;

import com.inditex.project.entity.Price;
import com.inditex.project.infrastructure.service.implementation.PriceServiceImpl;
import com.inditex.project.repository.PriceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PriceServiceImplTest {

    @Mock
    private PriceRepository repo;

    private PriceServiceImpl service;

    @BeforeEach
    void setUp() {
        service = new PriceServiceImpl(repo);
    }

    @Test
    void getApplicablePrice_returnsPriceWithHighestPriority() {
        LocalDateTime date = LocalDateTime.of(2020, 6, 14, 10, 0);
        Long productId = 35455L;
        Long brandId = 1L;

        Price lowPriority = mock(Price.class);
        when(lowPriority.getPriority()).thenReturn(0);

        Price highPriority = mock(Price.class);
        when(highPriority.getPriority()).thenReturn(1);

        List<Price> candidates = List.of(lowPriority, highPriority);
        when(repo.findByProductIdAndBrandIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
                productId, brandId, date, date)).thenReturn(candidates);

        Optional<Price> result = service.getApplicablePrice(date, productId, brandId);

        assertTrue(result.isPresent());
        assertSame(highPriority, result.get());
    }

    @Test
    void getApplicablePrice_returnsEmptyWhenNoCandidates() {
        LocalDateTime date = LocalDateTime.now();
        Long productId = 1L;
        Long brandId = 2L;

        when(repo.findByProductIdAndBrandIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
                productId, brandId, date, date)).thenReturn(Collections.emptyList());

        Optional<Price> result = service.getApplicablePrice(date, productId, brandId);

        assertTrue(result.isEmpty());
    }
}
