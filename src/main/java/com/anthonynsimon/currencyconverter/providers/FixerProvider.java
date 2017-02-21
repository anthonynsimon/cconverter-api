package com.anthonynsimon.currencyconverter.providers;

import com.anthonynsimon.currencyconverter.model.Currency;
import com.anthonynsimon.currencyconverter.model.ExchangeRates;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@Qualifier("fixer.io")
public final class FixerProvider implements ExchangeRateProvider {

    private RestTemplate restTemplate;

    private final static String RESOURCE_URL = "http://api.fixer.io/latest?base=%s";

    public FixerProvider() {
        restTemplate = new RestTemplate();
    }

    @Override
    public ExchangeRates getExchangeRates(Currency base) {
        return restTemplate.getForObject(String.format(RESOURCE_URL, base), ExchangeRates.class);
    }
}
