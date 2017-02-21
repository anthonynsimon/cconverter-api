package com.anthonynsimon.currencyconverter.services;

import com.anthonynsimon.currencyconverter.model.Currency;
import com.anthonynsimon.currencyconverter.model.ExchangeRates;
import com.anthonynsimon.currencyconverter.providers.ExchangeRateProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public final class ExchangeRateService {

    // Cache rates for a reasonable time, rates of the same base do not need to be fetched for every request.
    private final static Duration CACHE_EXPIRATION = Duration.ofMinutes(60);

    private final Map<Currency, ExchangeRates> cachedRates;

    @Autowired
    @Qualifier("fixer.io")
    private ExchangeRateProvider exchangeRateProvider;

    public ExchangeRateService() {
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
            exchangeRates = exchangeRateProvider.getExchangeRates(from);
            exchangeRates.setDateFetched(LocalDateTime.now());
            cachedRates.put(from, exchangeRates);
        }

        return exchangeRates;
    }
}
