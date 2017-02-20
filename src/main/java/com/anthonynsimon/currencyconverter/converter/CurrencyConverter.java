package com.anthonynsimon.currencyconverter.converter;

import com.anthonynsimon.currencyconverter.model.Currency;
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

    // TODO: throw custom exception
    public BigDecimal convert(Currency to, BigDecimal value) throws NullPointerException {
        BigDecimal exchangeRate = exchangeRates.getRates().get(to);
        if (exchangeRate == null) {
            throw new NullPointerException("couldn't get exchange rate for " + to);
        }

        return exchangeRate.multiply(value)
                .setScale(4, BigDecimal.ROUND_HALF_EVEN)
                .stripTrailingZeros();
    }
}
