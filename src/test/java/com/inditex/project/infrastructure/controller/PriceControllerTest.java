package com.inditex.project.infrastructure.controller;

import com.inditex.project.application.mapper.PriceMapper;
import com.inditex.project.entity.Price;
import com.inditex.project.infrastructure.controller.model.PriceResponse;
import com.inditex.project.infrastructure.service.PriceService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PriceControllerTest {

    @Mock
    private PriceService service;

    @Test
    void getPrice_returnsMappedResponse_whenPricePresent() {
        PriceController controller = new PriceController(service);

        LocalDateTime date = LocalDateTime.of(2020, 6, 14, 10, 0, 0);
        Long productId = 35455L;
        Long brandId = 1L;

        Price price = mock(Price.class);
        when(service.getApplicablePrice(date, productId, brandId)).thenReturn(Optional.of(price));

        PriceResponse expectedResponse = mock(PriceResponse.class);

        try (MockedStatic<PriceMapper> mocked = mockStatic(PriceMapper.class)) {
            mocked.when(() -> PriceMapper.toResponse(price)).thenReturn(expectedResponse);

            PriceResponse actual = controller.getPrice(date, productId, brandId);

            assertSame(expectedResponse, actual);
            verify(service).getApplicablePrice(date, productId, brandId);
            mocked.verify(() -> PriceMapper.toResponse(price));
        }
    }

    @Test
    void getPrice_throwsNotFound_whenNoPrice() {
        PriceController controller = new PriceController(service);

        LocalDateTime date = LocalDateTime.of(2020, 6, 14, 10, 0, 0);
        Long productId = 123L;
        Long brandId = 2L;

        when(service.getApplicablePrice(date, productId, brandId)).thenReturn(Optional.empty());

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> controller.getPrice(date, productId, brandId));

        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
        assertNotNull(ex.getReason());
        assertTrue(ex.getReason().contains("productId=" + productId));
        assertTrue(ex.getReason().contains("brandId=" + brandId));
    }
}
