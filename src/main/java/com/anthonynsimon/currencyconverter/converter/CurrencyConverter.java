package com.anthonynsimon.currencyconverter.converter;

import com.anthonynsimon.currencyconverter.model.Currency;
import com.anthonynsimon.currencyconverter.model.ExchangeQuote;
import com.anthonynsimon.currencyconverter.model.ExchangeRates;

import java.math.BigDecimal;

public final class CurrencyConverter {

    private final ExchangeRates exchangeRates;

    // TODO: clean up the converter's API
    public CurrencyConverter(ExchangeRates baseExchangeRates) {
        this.exchangeRates = baseExchangeRates;
    }

    public Currency getBaseCurrency() {
        return exchangeRates.getBaseCurrency();
    }

    // TODO: throw custom exception
    public ExchangeQuote convert(Currency to, BigDecimal value) throws NullPointerException {
        BigDecimal exchangeRate = exchangeRates.getRates().get(to);
        if (exchangeRate == null) {
            throw new NullPointerException("couldn't get exchange rate for " + to);
        }

        BigDecimal conversionResult = exchangeRate.multiply(value)
                .setScale(4, BigDecimal.ROUND_HALF_EVEN)
                .stripTrailingZeros();

        return new ExchangeQuote.Builder()
                .setConversionResult(conversionResult)
                .setAmountToConvert(value)
                .setToCurrency(to)
                .setFromCurrency(getBaseCurrency())
                .setDate(exchangeRates.getDate())
                .setExchangeRate(exchangeRate)
                .build();
    }
}
