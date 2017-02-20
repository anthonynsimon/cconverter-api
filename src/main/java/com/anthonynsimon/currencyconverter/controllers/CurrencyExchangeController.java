package com.anthonynsimon.currencyconverter.controllers;

import com.anthonynsimon.currencyconverter.converter.CurrencyConverter;
import com.anthonynsimon.currencyconverter.model.Currency;
import com.anthonynsimon.currencyconverter.model.ExchangeQuote;
import com.anthonynsimon.currencyconverter.model.ExchangeRates;
import com.anthonynsimon.currencyconverter.services.ExchangeRateService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Controller
@RequestMapping("/api")
public final class CurrencyExchangeController {

    @Autowired
    ExchangeRateService exchangeRateService;

    private ObjectMapper objectMapper;

    public CurrencyExchangeController() {
        objectMapper = new ObjectMapper();
    }

    @RequestMapping(value = "/convert", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ExchangeQuote convertCurrency(@RequestParam String from,
                                         @RequestParam String to,
                                         @RequestParam String value) throws Exception {
        Currency fromCurrency = new Currency(from);
        Currency toCurrency = new Currency(to);

        if (!fromCurrency.isValid() || !toCurrency.isValid()) {
            throw new Exception("invalid currency code");
        }

        BigDecimal inputValue = new BigDecimal(value);

        // Handle case when from equals to
        if (fromCurrency.getCode().equals(toCurrency.getCode())) {
            return new ExchangeQuote.Builder()
                    .setAmountToConvert(inputValue)
                    .setFromCurrency(fromCurrency)
                    .setToCurrency(toCurrency)
                    .setConversionResult(inputValue)
                    .setExchangeRate(BigDecimal.ONE)
                    .setDate(LocalDate.now())
                    .build();
        }

        ExchangeRates baseRates = exchangeRateService.getExchangeRates(fromCurrency);
        CurrencyConverter converter = new CurrencyConverter(baseRates);

        return converter.convert(toCurrency, inputValue);
    }

    @RequestMapping(value = "/rates/{currency}", method = RequestMethod.GET)
    @ResponseBody
    public String convertCurrency(@PathVariable(name = "currency") String currencyCode) throws Exception {
        Currency currency = new Currency(currencyCode);

        if (!currency.isValid()) {
            throw new Exception("invalid currency code");
        }

        ExchangeRates rates = exchangeRateService.getExchangeRates(currency);
        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(rates);
    }
}
