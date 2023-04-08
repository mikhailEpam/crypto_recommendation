package com.recomendation.crypto.model.request;

import lombok.Data;

import java.util.Set;

@Data
public class ExclusionCurrencyRequest {
    private Set<String> currencies;
}
