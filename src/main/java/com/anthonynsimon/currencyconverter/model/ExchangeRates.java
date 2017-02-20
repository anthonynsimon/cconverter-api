package com.anthonynsimon.currencyconverter.model;

import com.anthonynsimon.currencyconverter.model.serializers.LocalDateDeserializer;
import com.anthonynsimon.currencyconverter.model.serializers.LocalDateSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
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

    @JsonIgnore
    private LocalDateTime dateFetched;

    public ExchangeRates(Currency baseCurrency, LocalDate date, Map<Currency, BigDecimal> exchangeRates) {
        this.baseCurrency = baseCurrency;
        this.date = date;
        this.exchangeRates = exchangeRates;
    }

    // For JSON serialization via Jackson
    protected ExchangeRates() {
    }

    public LocalDateTime getDateFetched() {
        return dateFetched;
    }

    public void setDateFetched(LocalDateTime dateFetched) {
        this.dateFetched = dateFetched;
    }

    public Currency getBaseCurrency() {
        return baseCurrency;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
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
