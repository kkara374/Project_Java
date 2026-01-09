package com.inditex.project.application.mapper;

import com.inditex.project.infrastructure.controller.model.PriceResponse;
import com.inditex.project.entity.Price;

public final class PriceMapper {
    private PriceMapper() {}

    public static PriceResponse toResponse(Price price) {
        return new PriceResponse(
                price.getProductId(),
                price.getBrandId(),
                price.getPriceList(),
                price.getStartDate(),
                price.getEndDate(),
                price.getPrice(),
                price.getCurr()
        );
    }
}

