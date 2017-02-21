package com.anthonynsimon.cconverter.services;

import com.anthonynsimon.cconverter.model.Currency;
import com.anthonynsimon.cconverter.model.ExchangeRates;
import com.anthonynsimon.cconverter.providers.ExchangeRateProvider;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ExchangeRateServiceTest {

    @Mock
    private ExchangeRateProvider exchangeRateProvider;

    @InjectMocks
    private ExchangeRateService service;

    @Before
    public void setupMock() {
        ArgumentCaptor<Currency> currencyCaptor = ArgumentCaptor.forClass(Currency.class);

        final Answer<ExchangeRates> exchangeRatesAnswer = new Answer<ExchangeRates>() {
            @Override
            public ExchangeRates answer(InvocationOnMock invocation) throws Throwable {
                return new ExchangeRates(currencyCaptor.getValue(), LocalDate.now(), new HashMap<Currency, BigDecimal>() {
                    {
                        put(new Currency("EUR"), BigDecimal.valueOf(1.5));
                        put(new Currency("USD"), BigDecimal.valueOf(2.5));
                        put(new Currency("JPY"), BigDecimal.valueOf(3.5));
                    }
                });
            }
        };

        when(exchangeRateProvider.getExchangeRates(currencyCaptor.capture()))
                .thenAnswer(exchangeRatesAnswer);
    }

    @Test
    public void testGetRates() {
        Currency eur = new Currency("EUR");
        Currency usd = new Currency("USD");
        Currency jpy = new Currency("JPY");

        ExchangeRates eurRates = service.getExchangeRates(eur);
        assertThat(eurRates)
                .isNotNull()
                .matches(exchangeRates -> exchangeRates.getBaseCurrency().equals(eur), "has correct base currency")
                .matches(exchangeRates -> !exchangeRates.getRates().isEmpty(), "is not empty")
                .matches(exchangeRates -> exchangeRates.getRates().containsKey(usd), "contains USD rate")
                .matches(exchangeRates -> exchangeRates.getRates().containsKey(jpy), "contains JPY rate")
                .matches(exchangeRates -> exchangeRates.getDateFetched() != null, "has last fetched date");

        ExchangeRates usdRates = service.getExchangeRates(usd);
        assertThat(usdRates)
                .isNotNull()
                .matches(exchangeRates -> exchangeRates.getBaseCurrency().equals(usd), "has correct base currency")
                .matches(exchangeRates -> !exchangeRates.getRates().isEmpty(), "is not empty")
                .matches(exchangeRates -> exchangeRates.getRates().containsKey(eur), "contains EUR rate")
                .matches(exchangeRates -> exchangeRates.getRates().containsKey(jpy), "contains JPY rate")
                .matches(exchangeRates -> exchangeRates.getDateFetched() != null, "has last fetched date");

        ExchangeRates jpyRates = service.getExchangeRates(jpy);
        assertThat(jpyRates)
                .isNotNull()
                .matches(exchangeRates -> exchangeRates.getBaseCurrency().equals(jpy), "has correct base currency")
                .matches(exchangeRates -> !exchangeRates.getRates().isEmpty(), "is not empty")
                .matches(exchangeRates -> exchangeRates.getRates().containsKey(usd), "contains USD rate")
                .matches(exchangeRates -> exchangeRates.getRates().containsKey(eur), "contains EUR rate")
                .matches(exchangeRates -> exchangeRates.getDateFetched() != null, "has last fetched date");
    }

    @Test
    public void testGetCachedRates() {
        Currency eur = new Currency("EUR");

        ExchangeRates eurRates = service.getExchangeRates(eur);
        ExchangeRates eurRates2 = service.getExchangeRates(eur);
        ExchangeRates eurRates3 = service.getExchangeRates(eur);

        assertThat(eurRates)
                .isEqualToComparingFieldByField(eurRates2)
                .isEqualToComparingFieldByField(eurRates3);
    }
}
