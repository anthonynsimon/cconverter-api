package com.anthonynsimon.cconverter.model;

import com.anthonynsimon.cconverter.model.serializers.CurrencySerializer;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.HashSet;
import java.util.Set;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSerialize(using = CurrencySerializer.class)
public final class Currency {

    private String currencyCode;

    private static final Set<String> KNOWN_CODES = new HashSet<String>() {{
        add("AUD");
        add("BGN");
        add("BRL");
        add("CAD");
        add("CHF");
        add("CNY");
        add("CZK");
        add("DKK");
        add("GBP");
        add("HKD");
        add("HRK");
        add("HUF");
        add("IDR");
        add("ILS");
        add("INR");
        add("JPY");
        add("KRW");
        add("MXN");
        add("MYR");
        add("NOK");
        add("NZD");
        add("USD");
        add("PHP");
        add("PLN");
        add("RON");
        add("RUB");
        add("SEK");
        add("SGD");
        add("THB");
        add("TRY");
        add("ZAR");
        add("EUR");
    }};

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
