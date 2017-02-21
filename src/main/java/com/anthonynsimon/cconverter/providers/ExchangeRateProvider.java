package com.anthonynsimon.cconverter.providers;

import com.anthonynsimon.cconverter.model.Currency;
import com.anthonynsimon.cconverter.model.ExchangeRates;

/**
 * ExchangeRateProvider is a facade for an external system which provides the
 * actual exchange rates.
 */
public interface ExchangeRateProvider {

    /**
     * Returns the {@link ExchangeRates} for the provided {@link Currency}.
     */
    ExchangeRates getExchangeRates(Currency base);
}
