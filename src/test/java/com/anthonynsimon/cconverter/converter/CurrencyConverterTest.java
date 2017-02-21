package com.anthonynsimon.cconverter.converter;

import com.anthonynsimon.cconverter.exceptions.NotFoundException;
import com.anthonynsimon.cconverter.model.Currency;
import com.anthonynsimon.cconverter.model.ExchangeQuote;
import com.anthonynsimon.cconverter.model.ExchangeRates;
import org.assertj.core.data.Percentage;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;

public class CurrencyConverterTest {

    private ExchangeRates exchangeRates;
    private CurrencyConverter currencyConverter;
    private Currency fromCurrency;

    @Before
    public void setup() {
        fromCurrency = new Currency("EUR");
        exchangeRates = new ExchangeRates(fromCurrency, LocalDate.now(), new HashMap<Currency, BigDecimal>() {
            {
                put(new Currency("USD"), BigDecimal.valueOf(2.5));
                put(new Currency("JPY"), BigDecimal.valueOf(3.5));
            }
        });
        currencyConverter = new CurrencyConverter(exchangeRates);
    }

    @Test
    public void testSimpleConversion() {
        Currency toCurrency = new Currency("USD");
        ExchangeQuote quote = currencyConverter.convert(toCurrency, BigDecimal.valueOf(10));

        assertThat(quote)
                .isNotNull()
                .matches(result -> result.getToCurrency().equals(toCurrency), "to currency matches")
                .matches(result -> result.getFromCurrency().equals(fromCurrency), "base currency matches");

        assertThat(quote.getConversionResult()).isCloseTo(BigDecimal.valueOf(25.0), Percentage.withPercentage(1d));
        assertThat(quote.getAmountToConvert()).isCloseTo(BigDecimal.valueOf(10), Percentage.withPercentage(1d));
        assertThat(quote.getExchangeRate()).isCloseTo(BigDecimal.valueOf(2.5), Percentage.withPercentage(1d));
    }

    @Test(expected = NotFoundException.class)
    public void testUnknownCurrencyCode() {
        Currency toCurrency = new Currency("CRC");
        ExchangeQuote quote = currencyConverter.convert(toCurrency, BigDecimal.valueOf(10));
    }
}
