package com.anthonynsimon.cconverter.providers;

import com.anthonynsimon.cconverter.model.Currency;
import com.anthonynsimon.cconverter.model.ExchangeRates;

public interface ExchangeRateProvider {

    ExchangeRates getExchangeRates(Currency base);
}
