package com.anthonynsimon.currencyconverter.controllers;

import com.anthonynsimon.currencyconverter.converter.CurrencyConverter;
import com.anthonynsimon.currencyconverter.model.ExchangeRates;
import com.anthonynsimon.currencyconverter.services.ExchangeRateService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;

@Controller
@RequestMapping("/api")
public final class CurrencyExchangeController {

    @Autowired
    ExchangeRateService exchangeRateService;

    private ObjectMapper objectMapper;

    public CurrencyExchangeController() {
        objectMapper = new ObjectMapper();
    }

    @RequestMapping("/convert")
    @ResponseBody
    public String convertCurrency(@RequestParam String from, @RequestParam String to, @RequestParam String value) {
        // TODO: sanitize inputs, convert to upper case, etc...
        // TODO: handle exceptions from unknown inputs
        BigDecimal inputValue = new BigDecimal(value);
        ExchangeRates baseRates = exchangeRateService.getExchangeRates(from);
        CurrencyConverter converter = new CurrencyConverter(baseRates);
        return converter.convert(to, inputValue).toPlainString();
    }

    @RequestMapping("/rates/{currency}")
    @ResponseBody
    public String convertCurrency(@PathVariable String currency) throws Exception {
        // TODO: sanitize inputs, convert to upper case, etc...
        // TODO: handle exceptions from unknown inputs
        ExchangeRates rates = exchangeRateService.getExchangeRates(currency);
        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(rates);
    }
}
