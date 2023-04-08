package com.recomendation.crypto.controller;

import com.recomendation.crypto.model.request.DateRequest;
import com.recomendation.crypto.service.RecommendationService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/api/v1/recommendation")
public class RecommendationController {

    private RecommendationService recommendationService;

    @GetMapping(value = "/normalized/all")
    public List<String> getAllNormalizedCurrencies(DateRequest dateRequest) {
        return recommendationService.getAllNormalizedCurrencies(dateRequest.getStartTime(), dateRequest.getEndTime());
    }

    @GetMapping(value = "/normalized/highest")
    public String getHighestNormalizedCurrency(DateRequest dateRequest) {
        return recommendationService.getHighestNormalizedCurrency(dateRequest.getStartTime(), dateRequest.getEndTime()).orElse(null);
    }

    @GetMapping(value = "/{currency}/max")
    public BigDecimal getMaxCurrencyValue(@PathVariable String currency,
                                          DateRequest dateRequest) {
        return recommendationService.findMax(currency, dateRequest.getStartTime(), dateRequest.getEndTime()).orElse(null);
    }

    @GetMapping(value = "/{currency}/min")
    public BigDecimal getMinCurrencyValue(@PathVariable String currency,
                                          DateRequest dateRequest) {
        return recommendationService.findMin(currency, dateRequest.getStartTime(), dateRequest.getEndTime()).orElse(null);
    }

    @GetMapping(value = "/{currency}/oldest")
    public BigDecimal getOldestCurrencyValue(@PathVariable String currency,
                                             DateRequest dateRequest) {
        return recommendationService.findOldest(currency, dateRequest.getStartTime(), dateRequest.getEndTime()).orElse(null);
    }

    @GetMapping(value = "/{currency}/newest")
    public BigDecimal getNewestCurrencyValue(@PathVariable String currency,
                                             DateRequest dateRequest) {
        return recommendationService.findNewest(currency, dateRequest.getStartTime(), dateRequest.getEndTime()).orElse(null);
    }

}
