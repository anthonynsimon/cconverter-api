package com.anthonynsimon.currencyconverter.services;

import com.anthonynsimon.currencyconverter.model.Currency;
import com.anthonynsimon.currencyconverter.model.ExchangeRates;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public final class ExchangeRateService {

    private RestTemplate restTemplate;
    private final static String RESOURCE_URL = "http://api.fixer.io/latest?base=%s";

    // TODO: is this needed?
    public ExchangeRateService() {
        restTemplate = new RestTemplate();
    }

    // TODO: cache rates for reasonably time, rates do not need to be updated for every request
    public ExchangeRates getExchangeRates(Currency from) {
        return restTemplate.getForObject(String.format(RESOURCE_URL, from), ExchangeRates.class);
    }
}
