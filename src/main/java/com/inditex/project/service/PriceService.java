package com.inditex.project.service;

import com.inditex.project.entity.Price;
import com.inditex.project.repository.PriceRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Service
public class PriceService {

    private final PriceRepository repo;

    public PriceService(PriceRepository repo) {
        this.repo = repo;
    }

    public Price getApplicablePrice(LocalDateTime date, Long productId, Long brandId) {
        List<Price> candidates =
                repo.findByProductIdAndBrandIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
                        productId, brandId, date, date);

        return candidates.stream()
                .max(Comparator.comparingInt(Price::getPriority))
                .orElse(null);
    }
}

