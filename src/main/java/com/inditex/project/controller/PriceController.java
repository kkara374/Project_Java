package com.inditex.project.controller;

import com.inditex.project.dto.PriceResponse;
import com.inditex.project.entity.Price;
import com.inditex.project.service.PriceService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/prices")
public class PriceController {

    private final PriceService service;

    public PriceController(PriceService service) {
        this.service = service;
    }

    @GetMapping
    public PriceResponse getPrice(
            @RequestParam
            @DateTimeFormat(pattern = "yyyy-MM-dd-HH.mm.ss") LocalDateTime date,
            @RequestParam Long productId,
            @RequestParam Long brandId
    ) {
        Price p = service.getApplicablePrice(date, productId, brandId);
        if (p == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "No applicable price for productId=" + productId +
                            ", brandId=" + brandId +
                            ", date=" + date);
        }
        return new PriceResponse(
                p.getProductId(),
                p.getBrandId(),
                p.getPriceList(),
                p.getStartDate(),
                p.getEndDate(),
                p.getPrice(),
                p.getCurr()
        );
    }
}

