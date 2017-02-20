package com.anthonynsimon.currencyconverter.services;

import com.anthonynsimon.currencyconverter.model.Currency;
import com.anthonynsimon.currencyconverter.model.ExchangeRates;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public final class ExchangeRateService {

    // Cache rates for a reasonable time, rates of the same base do not need to be fetched for every request.
    private final static Duration CACHE_EXPIRATION = Duration.ofMinutes(60);

    private RestTemplate restTemplate;
    private final static String RESOURCE_URL = "http://api.fixer.io/latest?base=%s";
    private final Map<Currency, ExchangeRates> cachedRates;

    public ExchangeRateService() {
        restTemplate = new RestTemplate();
        cachedRates = new HashMap<>();
    }

    public ExchangeRates getExchangeRates(Currency from) {
        ExchangeRates exchangeRates = cachedRates.get(from);
        boolean shouldUpdate = true;
        if (exchangeRates != null) {
            if (LocalDateTime.now().minus(CACHE_EXPIRATION).compareTo(exchangeRates.getDateFetched()) > 0) {
                shouldUpdate = true;
            } else {
                shouldUpdate = false;
            }
        }

        if (shouldUpdate) {
            exchangeRates = restTemplate.getForObject(String.format(RESOURCE_URL, from), ExchangeRates.class);
            exchangeRates.setDateFetched(LocalDateTime.now());
            cachedRates.put(from, exchangeRates);
        }

        return exchangeRates;
    }
}
