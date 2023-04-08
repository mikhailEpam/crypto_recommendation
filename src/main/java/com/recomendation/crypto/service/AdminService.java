package com.recomendation.crypto.service;

import com.recomendation.crypto.model.request.ExclusionCurrencyRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

@Service
public class AdminService {

    @Lazy
    @Autowired
    private RecommendationService recommendationService;

    private final Set<String> excludedCurrencies = new ConcurrentSkipListSet<>();

    public Set<String> getExcludedCurrencies() {
        return excludedCurrencies;
    }

    public void addCurrencyToExclusion(ExclusionCurrencyRequest exclusionRequest) {
        excludedCurrencies.addAll(exclusionRequest.getCurrencies());
        recommendationService.updateCurrency();
    }

    public void removeCurrencyFromExclusion(ExclusionCurrencyRequest exclusionRequest) {
        excludedCurrencies.removeAll(exclusionRequest.getCurrencies());
        recommendationService.updateCurrency();
    }

}
