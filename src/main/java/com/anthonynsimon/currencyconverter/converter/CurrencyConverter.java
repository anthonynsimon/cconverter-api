package com.anthonynsimon.currencyconverter.converter;

import com.anthonynsimon.currencyconverter.model.ExchangeRates;

import java.math.BigDecimal;

public final class CurrencyConverter {

    private final ExchangeRates exchangeRates;

    // TODO: clean up the converter's API
    public CurrencyConverter(ExchangeRates baseExchangeRates) {
        this.exchangeRates = baseExchangeRates;
    }

    public String getBaseCurrency() {
        return exchangeRates.getBaseCurrency();
    }

    public BigDecimal convert(String to, BigDecimal value) {
        return exchangeRates.getRates().get(to)
                .multiply(value)
                .setScale(4, BigDecimal.ROUND_HALF_EVEN)
                .stripTrailingZeros();
    }
}
