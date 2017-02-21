package com.anthonynsimon.cconverter.model;

import com.anthonynsimon.cconverter.model.serializers.CurrencySerializer;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.Arrays;
import java.util.HashSet;


/**
 * Currency is a helper class for the parsing and validation of currency codes.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSerialize(using = CurrencySerializer.class)
public final class Currency {

    private static final HashSet<String> KNOWN_CODES = new HashSet<String>(
            Arrays.asList("AUD", "BGN", "BRL", "CAD", "CHF", "CNY", "CZK", "DKK",
                    "GBP", "HKD", "HRK", "HUF", "IDR", "ILS", "INR", "JPY", "KRW",
                    "MXN", "MYR", "NOK", "NZD", "USD", "PHP", "PLN", "RON", "RUB",
                    "SEK", "SGD", "THB", "TRY", "ZAR", "EUR"));

    private String currencyCode;

    public Currency(String currencyCode) {
        if (currencyCode != null) {
            currencyCode = currencyCode.toUpperCase();
        }
        this.currencyCode = currencyCode;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public boolean isValid() {
        if (currencyCode == null || currencyCode.isEmpty()) {
            return false;
        }
        if (currencyCode.length() != 3) {
            return false;
        }
        return KNOWN_CODES.contains(currencyCode);
    }

    @Override
    public String toString() {
        return currencyCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Currency currency = (Currency) o;

        return currencyCode.equals(currency.currencyCode);
    }

    @Override
    public int hashCode() {
        return currencyCode.hashCode();
    }
}
