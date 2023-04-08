package com.recomendation.crypto.controller;

import com.recomendation.crypto.RecommendationApplication;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = RecommendationApplication.class)
@AutoConfigureMockMvc
class RecommendationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getAllNormalizedCurrencies() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/recommendation/normalized/all")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(5)));
    }

    @Test
    void getHighestNormalizedCurrency() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/recommendation/normalized/highest")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is("ETH")));
    }

    @Test
    void getMaxCurrencyValue() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/recommendation/BTC/max")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.comparesEqualTo(47722.66)));
    }

    @Test
    void getMinCurrencyValue() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/recommendation/BTC/min")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.comparesEqualTo(33276.59)));
    }

    @Test
    void getOldestCurrencyValue() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/recommendation/BTC/oldest")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.comparesEqualTo(46813.21)));
    }

    @Test
    void getNewestCurrencyValue() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/recommendation/BTC/newest")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.comparesEqualTo(38415.79)));
    }
}