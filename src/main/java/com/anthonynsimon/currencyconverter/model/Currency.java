package com.anthonynsimon.currencyconverter.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.HashSet;
import java.util.Set;

@JsonIgnoreProperties(ignoreUnknown = true)
public final class Currency {



    private String code;

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

    public Currency(String code) {
        if (code != null) {
            code = code.toUpperCase();
        }
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public boolean isValid() {
        if (code == null || code.isEmpty()) {
            return false;
        }
        if (code.length() != 3) {
            return false;
        }
        return KNOWN_CODES.contains(code);
    }

    @Override
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }
        if (!(other instanceof Currency)) {
            return false;
        }
        return this.hashCode() == other.hashCode();
    }

    @Override
    public int hashCode() {
        return code.hashCode();
    }

    @Override
    public String toString() {
        return code;
    }
}