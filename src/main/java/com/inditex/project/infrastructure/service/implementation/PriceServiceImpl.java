package com.inditex.project.infrastructure.service.implementation;

import com.inditex.project.entity.Price;
import com.inditex.project.infrastructure.service.PriceService;
import com.inditex.project.repository.PriceRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Optional;

@Service
public class PriceServiceImpl implements PriceService {

    private final PriceRepository repo;

    public PriceServiceImpl(PriceRepository repo) {
        this.repo = repo;
    }

    public Optional<Price> getApplicablePrice(LocalDateTime date, Long productId, Long brandId) {
        var candidates = repo.findByProductIdAndBrandIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
                productId, brandId, date, date);
        return candidates.stream()
                .max(Comparator.comparingInt(Price::getPriority));
    }

}

