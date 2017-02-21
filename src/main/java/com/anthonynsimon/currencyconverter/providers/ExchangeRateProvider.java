package com.anthonynsimon.currencyconverter.providers;

import com.anthonynsimon.currencyconverter.model.Currency;
import com.anthonynsimon.currencyconverter.model.ExchangeRates;

public interface ExchangeRateProvider {

    ExchangeRates getExchangeRates(Currency base);
}
