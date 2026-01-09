package com.inditex.project.infrastructure.controller;

import com.inditex.project.infrastructure.controller.model.PriceResponse;
import com.inditex.project.entity.Price;
import com.inditex.project.infrastructure.service.PriceService;
import com.inditex.project.application.mapper.PriceMapper;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/prices")
@RequiredArgsConstructor
@Validated
public class PriceController {

    @Autowired
    private final PriceService service;

    @GetMapping
    public PriceResponse getPrice(
            @RequestParam
            @NotNull(message = "date must not be null")
            @DateTimeFormat(pattern = "yyyy-MM-dd-HH.mm.ss") LocalDateTime date,
            @RequestParam @NotNull(message = "productId must not be null") Long productId,
            @RequestParam @Min(1) @Max(4) Long brandId
    ) {

        Price price = service.getApplicablePrice(date, productId, brandId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "No applicable price for productId=%d, brandId=%d, date=%s"
                                .formatted(productId, brandId, date)
                ));

        return PriceMapper.toResponse(price);
    }
}

