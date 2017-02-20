package com.anthonynsimon.currencyconverter.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public final class ExchangeRates {

    @JsonProperty("base")
    private String baseCurrency;

    @JsonProperty("rates")
    private Map<String, BigDecimal> exchangeRates;

    // TODO: serialize date to LocalDateTime type
    @JsonProperty("date")
    private String date;

    public ExchangeRates(String baseCurrency, String date, Map<String, BigDecimal> exchangeRates) {
        this.baseCurrency = baseCurrency;
        this.date = date;
        this.exchangeRates = exchangeRates;
    }

    protected ExchangeRates() {

    }

    public String getBaseCurrency() {
        return baseCurrency;
    }

    public String getDate() {
        return date;
    }

    public Map<String, BigDecimal> getRates() {
        return exchangeRates;
    }

    @Override
    public String toString() {
        return String.format("Base Currency: '%s'. Date: '%s'. Rates: %s",
                baseCurrency, date, exchangeRates.toString());
    }
}
