package com.recomendation.crypto.scheduler;

import com.recomendation.crypto.service.RecommendationService;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CryptoCurrencyScheduler {

    private RecommendationService recommendationService;

    @PostConstruct
    public void onStartup() {
        updateCryptoCurrency();
    }

    /**
     * Updates currencies every 5 minutes
     */
    @Scheduled(cron = "* */5 * * * *")
    public void updateCryptoCurrency() {
        recommendationService.updateCurrency();
    }
}
