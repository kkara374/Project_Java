package com.inditex.project.repository;

import com.inditex.project.entity.Price;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface PriceRepository extends JpaRepository<Price, Long> {

    List<Price> findByProductIdAndBrandIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
            Long productId,
            Long brandId,
            LocalDateTime dateBeforeOrEqual,
            LocalDateTime dateAfterOrEqual
    );
}

