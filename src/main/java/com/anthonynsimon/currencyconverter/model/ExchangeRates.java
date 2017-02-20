package com.anthonynsimon.currencyconverter.model;

import com.anthonynsimon.currencyconverter.model.serializers.LocalDateDeserializer;
import com.anthonynsimon.currencyconverter.model.serializers.LocalDateSerializer;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public final class ExchangeRates {

    @JsonProperty("base")
    private Currency baseCurrency;

    @JsonProperty("rates")
    private Map<Currency, BigDecimal> exchangeRates;

    @JsonProperty("date")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate date;

    public ExchangeRates(Currency baseCurrency, LocalDate date, Map<Currency, BigDecimal> exchangeRates) {
        this.baseCurrency = baseCurrency;
        this.date = date;
        this.exchangeRates = exchangeRates;
    }

    // For Jackson
    protected ExchangeRates() {
    }

    public Currency getBaseCurrency() {
        return baseCurrency;
    }

    public LocalDate getDate() {
        return date;
    }

    public Map<Currency, BigDecimal> getRates() {
        return exchangeRates;
    }

    @Override
    public String toString() {
        return String.format("Base Currency: '%s'. Date: '%s'. Rates: %s",
                baseCurrency, date, exchangeRates.toString());
    }
}
