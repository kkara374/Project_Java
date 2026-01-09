package com.inditex.project.infrastructure.service;

import com.inditex.project.entity.Price;

import java.time.LocalDateTime;
import java.util.Optional;

public interface PriceService {

    public Optional<Price> getApplicablePrice(LocalDateTime date, Long productId, Long brandId);
}
