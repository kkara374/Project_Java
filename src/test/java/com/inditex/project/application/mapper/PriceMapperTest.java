package com.inditex.project.application.mapper;

import com.inditex.project.entity.Price;
import com.inditex.project.infrastructure.controller.model.PriceResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PriceMapperTest {

    @Mock
    private Price price;

    @Test
    void toResponse_mapsAllFields() {
        long expectedProductId = 123L;
        long expectedBrandId = 1L;
        int expectedPriceList = 2;
        LocalDateTime expectedStart = LocalDateTime.of(2020, 6, 14, 0, 0);
        LocalDateTime expectedEnd = LocalDateTime.of(2020, 12, 31, 23, 59);
        BigDecimal expectedPrice = new BigDecimal("35.50");
        String expectedCurr = "EUR";

        when(price.getProductId()).thenReturn(expectedProductId);
        when(price.getBrandId()).thenReturn(expectedBrandId);
        when(price.getPriceList()).thenReturn(expectedPriceList);
        when(price.getStartDate()).thenReturn(expectedStart);
        when(price.getEndDate()).thenReturn(expectedEnd);
        when(price.getPrice()).thenReturn(expectedPrice.doubleValue());
        when(price.getCurr()).thenReturn(expectedCurr);

        PriceResponse response = PriceMapper.toResponse(price);

        assertEquals(expectedProductId, response.getProductId());
        assertEquals(expectedBrandId, response.getBrandId());
        assertEquals(expectedPriceList, response.getPriceList());
        assertEquals(expectedStart, response.getStartDate());
        assertEquals(expectedEnd, response.getEndDate());
        assertEquals(0, expectedPrice.compareTo(BigDecimal.valueOf(response.getPrice())));
        assertEquals(expectedCurr, response.getCurr());
    }
}

