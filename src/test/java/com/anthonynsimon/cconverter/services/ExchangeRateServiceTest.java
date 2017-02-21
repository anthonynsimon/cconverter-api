package com.anthonynsimon.cconverter.services;

import com.anthonynsimon.cconverter.model.Currency;
import com.anthonynsimon.cconverter.model.ExchangeRates;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ExchangeRateServiceTest {

    @Autowired
    private ExchangeRateService service;

    @Test
    public void contexLoads() throws Exception {
        assertThat(service).isNotNull();
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
