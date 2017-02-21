package com.anthonynsimon.cconverter.model;

import com.anthonynsimon.cconverter.model.serializers.LocalDateDeserializer;
import com.anthonynsimon.cconverter.model.serializers.LocalDateSerializer;
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

    @JsonProperty("date")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate date;

    @JsonProperty("base")
    private Currency baseCurrency;

    @JsonProperty("rates")
    private Map<Currency, BigDecimal> exchangeRates;

    @JsonIgnore
    private LocalDateTime dateFetched;

    // For JSON serialization via Jackson
    protected ExchangeRates() {
    }

    public ExchangeRates(Currency baseCurrency, LocalDate date, Map<Currency, BigDecimal> exchangeRates) {
        this.baseCurrency = baseCurrency;
        this.date = date;
        this.exchangeRates = exchangeRates;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ExchangeRates that = (ExchangeRates) o;

        if (!baseCurrency.equals(that.baseCurrency)) {
            return false;
        }
        if (!exchangeRates.equals(that.exchangeRates)) {
            return false;
        }
        if (!date.equals(that.date)) {
            return false;
        }
        return dateFetched.equals(that.dateFetched);
    }

    @Override
    public int hashCode() {
        int result = baseCurrency.hashCode();
        result = 31 * result + exchangeRates.hashCode();
        result = 31 * result + date.hashCode();
        result = 31 * result + dateFetched.hashCode();
        return result;
    }
}
