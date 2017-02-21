package com.anthonynsimon.cconverter.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.math.BigDecimal;

@JsonIgnoreProperties(ignoreUnknown = true)
public final class ExchangeQuote {

    private final Currency fromCurrency;
    private final Currency toCurrency;
    private final BigDecimal amountToConvert;
    private final BigDecimal conversionResult;
    private final BigDecimal exchangeRate;

    public ExchangeQuote(Currency fromCurrency, Currency toCurrency, BigDecimal amountToConvert, BigDecimal conversionResult, BigDecimal exchangeRate) {
        this.fromCurrency = fromCurrency;
        this.toCurrency = toCurrency;
        this.amountToConvert = amountToConvert;
        this.conversionResult = conversionResult;
        this.exchangeRate = exchangeRate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ExchangeQuote that = (ExchangeQuote) o;

        if (!fromCurrency.equals(that.fromCurrency)) {
            return false;
        }
        if (!toCurrency.equals(that.toCurrency)) {
            return false;
        }
        if (!amountToConvert.equals(that.amountToConvert)) {
            return false;
        }
        if (!exchangeRate.equals(that.exchangeRate)) {
            return false;
        }
        return conversionResult.equals(that.conversionResult);
    }


    public Currency getFromCurrency() {
        return fromCurrency;
    }

    public Currency getToCurrency() {
        return toCurrency;
    }

    public BigDecimal getAmountToConvert() {
        return amountToConvert;
    }

    public BigDecimal getConversionResult() {
        return conversionResult;
    }

    public BigDecimal getExchangeRate() {
        return exchangeRate;
    }


    @Override
    public int hashCode() {
        int result = fromCurrency.hashCode();
        result = 31 * result + toCurrency.hashCode();
        result = 31 * result + amountToConvert.hashCode();
        result = 31 * result + conversionResult.hashCode();
        return result;
    }

    public static class Builder {
        private Currency fromCurrency;
        private Currency toCurrency;
        private BigDecimal amountToConvert;
        private BigDecimal conversionResult;
        private BigDecimal exchangeRate;

        public Builder setFromCurrency(Currency fromCurrency) {
            this.fromCurrency = fromCurrency;
            return this;
        }

        public Builder setToCurrency(Currency toCurrency) {
            this.toCurrency = toCurrency;
            return this;
        }

        public Builder setAmountToConvert(BigDecimal amountToConvert) {
            this.amountToConvert = amountToConvert;
            return this;
        }

        public Builder setConversionResult(BigDecimal conversionResult) {
            this.conversionResult = conversionResult;
            return this;
        }

        public Builder setExchangeRate(BigDecimal exchangeRate) {
            this.exchangeRate = exchangeRate;
            return this;
        }

        public ExchangeQuote build() {
            return new ExchangeQuote(fromCurrency, toCurrency, amountToConvert, conversionResult, exchangeRate);
        }
    }
}
