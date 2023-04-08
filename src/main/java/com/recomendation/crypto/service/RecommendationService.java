package com.recomendation.crypto.service;

import com.recomendation.crypto.exception.CurrencyNotFoundException;
import com.recomendation.crypto.model.Currency;
import com.recomendation.crypto.utils.FileUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RecommendationService {

    private AdminService adminService;
    private FileUtils fileUtils;

    private final Map<String, ConcurrentSkipListMap<Long, BigDecimal>> currencies = new ConcurrentHashMap<>();

    public void updateCurrency() {
        Set<String> excludedSet = adminService.getExcludedCurrencies();
        if (!CollectionUtils.isEmpty(excludedSet)) {
            excludedSet.forEach(currencies::remove);
        }
        List<List<Currency>> currencyList = fileUtils.retrieveCurrencies();
        currencyList.stream()
                .flatMap(Collection::stream)
                .forEach(currency -> {
                    ConcurrentSkipListMap<Long, BigDecimal> treeMap = currencies.get(currency.getName());
                    if (Objects.isNull(treeMap)) {
                        treeMap = new ConcurrentSkipListMap<>();
                    }
                    treeMap.put(currency.getTimestamp(), currency.getPrice());
                    currencies.put(currency.getName(), treeMap);
                });
    }

    public Optional<BigDecimal> findMax(String name, Long startTime, Long endTime) {
        return find(name, startTime, endTime, subMap -> subMap.values().stream().max(BigDecimal::compareTo));
    }

    public Optional<BigDecimal> findMin(String name, Long startTime, Long endTime) {
        return find(name, startTime, endTime, subMap -> subMap.values().stream().min(BigDecimal::compareTo));
    }

    public Optional<BigDecimal> findOldest(String name, Long startTime, Long endTime) {
        return find(name, startTime, endTime, subMap -> Optional.ofNullable(subMap.firstEntry().getValue()));
    }

    public Optional<BigDecimal> findNewest(String name, Long startTime, Long endTime) {
        return find(name, startTime, endTime, subMap -> Optional.ofNullable(subMap.lastEntry().getValue()));
    }

    private Optional<BigDecimal> find(String name,
                                      Long startTime,
                                      Long endTime,
                                      Function<ConcurrentNavigableMap<Long, BigDecimal>, Optional<BigDecimal>> function) {
        ConcurrentSkipListMap<Long, BigDecimal> currencyMap = currencies.get(name);
        if (currencyMap != null) {
            ConcurrentNavigableMap<Long, BigDecimal> subMap = currencyMap.subMap(startTime, true, endTime, true);
            if (subMap.isEmpty()) {
                return Optional.empty();
            }
            return function.apply(subMap);
        }
        throw new CurrencyNotFoundException(name);
    }

    public List<String> getAllNormalizedCurrencies(Long startTime, Long endTime) {
        Map<BigDecimal, Set<String>> normalizedMap = new TreeMap<>(Collections.reverseOrder());
        for (String name : currencies.keySet()) {
            Optional<BigDecimal> max = findMax(name, startTime, endTime);
            Optional<BigDecimal> min = findMin(name, startTime, endTime);
            if (max.isPresent() && min.isPresent()) {
                BigDecimal normalizedValue = (max.get().subtract(min.get())).divide(min.get(), RoundingMode.CEILING);
                if (normalizedMap.containsKey(normalizedValue)) {
                    Set<String> currencies = normalizedMap.get(normalizedValue);
                    currencies.add(name);
                    normalizedMap.put(normalizedValue, currencies);
                } else {
                    Set<String> set = new HashSet<>();
                    set.add(name);
                    normalizedMap.put(normalizedValue, set);
                }
            }
        }
        return normalizedMap.values().stream().flatMap(Collection::stream).collect(Collectors.toList());
    }

    public Optional<String> getHighestNormalizedCurrency(Long startTime, Long endTime) {
        return getAllNormalizedCurrencies(startTime, endTime).stream().findFirst();
    }

}
