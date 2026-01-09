package com.inditex.project.infrastructure.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class PriceControllerIT {

    @Autowired
    private MockMvc mvc;

    @Test
    void test1_2020_06_14_10_00_brand1_product35455() throws Exception {
        mvc.perform(get("/prices")
                        .param("date", "2020-06-14-10.00.00")
                        .param("productId", "35455")
                        .param("brandId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.priceList").value(1))
                .andExpect(jsonPath("$.price").value(35.50));
    }

    @Test
    void test2_2020_06_14_16_00_brand1_product35455() throws Exception {
        mvc.perform(get("/prices")
                        .param("date", "2020-06-14-16.00.00")
                        .param("productId", "35455")
                        .param("brandId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.priceList").value(2))
                .andExpect(jsonPath("$.price").value(25.45));
    }

    @Test
    void test3_2020_06_14_21_00_brand1_product35455() throws Exception {
        mvc.perform(get("/prices")
                        .param("date", "2020-06-14-21.00.00")
                        .param("productId", "35455")
                        .param("brandId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.priceList").value(1))
                .andExpect(jsonPath("$.price").value(35.50));
    }

    @Test
    void test4_2020_06_15_10_00_brand1_product35455() throws Exception {
        mvc.perform(get("/prices")
                        .param("date", "2020-06-15-10.00.00")
                        .param("productId", "35455")
                        .param("brandId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.priceList").value(3))
                .andExpect(jsonPath("$.price").value(30.50));
    }

    @Test
    void test5_2020_06_16_21_00_brand1_product35455() throws Exception {
        mvc.perform(get("/prices")
                        .param("date", "2020-06-16-21.00.00")
                        .param("productId", "35455")
                        .param("brandId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.priceList").value(4))
                .andExpect(jsonPath("$.price").value(38.95));
    }

    @Test
    void brand_out_of_range_returns_400() throws Exception {
        mvc.perform(get("/prices")
                        .param("date", "2020-06-14-10.00.00")
                        .param("productId", "35455")
                        .param("brandId", "7"))
                .andExpect(status().isBadRequest());
    }
}
