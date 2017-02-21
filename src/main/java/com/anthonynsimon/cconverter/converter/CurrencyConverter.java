package com.anthonynsimon.cconverter.converter;

import com.anthonynsimon.cconverter.exceptions.NotFoundException;
import com.anthonynsimon.cconverter.model.Currency;
import com.anthonynsimon.cconverter.model.ExchangeQuote;
import com.anthonynsimon.cconverter.model.ExchangeRates;

import java.math.BigDecimal;

/**
 * CurrencyConverter is a utility class for the conversion between currencies.
 */
public final class CurrencyConverter {

    private final ExchangeRates baseRates;

    /**
     * Constructs a CurrencyConverter with the base currency set based on the provided {@link ExchangeRates}.
     */
    public CurrencyConverter(ExchangeRates baseRates) {
        this.baseRates = baseRates;
    }

    public Currency getBaseCurrency() {
        return baseRates.getBaseCurrency();
    }

    /**
     * Returns the resulting {@link ExchangeQuote} by converting the parameter {@BigDecimal} 'value' from the base
     * currency to the parameter {@link Currency} 'to'.
     */
    public ExchangeQuote convert(Currency to, BigDecimal value) throws NotFoundException {
        BigDecimal exchangeRate = baseRates.getRates().get(to);
        if (exchangeRate == null) {
            throw new NotFoundException(String.format("couldn't find exchange rate from %s to %s", getBaseCurrency(), to));
        }

        BigDecimal conversionResult = exchangeRate.multiply(value)
                .setScale(4, BigDecimal.ROUND_HALF_EVEN)
                .stripTrailingZeros();

        return new ExchangeQuote.Builder()
                .setConversionResult(conversionResult)
                .setAmountToConvert(value)
                .setToCurrency(to)
                .setFromCurrency(getBaseCurrency())
                .setExchangeRate(exchangeRate)
                .build();
    }
}
