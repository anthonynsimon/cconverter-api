package com.anthonynsimon.currencyconverter.services;

import com.anthonynsimon.currencyconverter.model.ExchangeRates;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

@Service
public final class ExchangeRateService {

    private RestTemplate restTemplate;

    // TODO: is this needed?
    public ExchangeRateService() {
        restTemplate = new RestTemplate();
    }

    // TODO: cache rates for reasonably time, rates do not need to be updated for every request
    public ExchangeRates getExchangeRates(String from) {
        return restTemplate.getForObject(String.format("http://api.fixer.io/latest?base=%s", from), ExchangeRates.class);
    }

    public BigDecimal getExchangeRate(String from, String to) {
        return getExchangeRates(from).getRates().get(to);
    }
}
