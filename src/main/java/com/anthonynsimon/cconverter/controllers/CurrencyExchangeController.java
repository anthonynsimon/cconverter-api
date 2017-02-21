package com.anthonynsimon.cconverter.controllers;

import com.anthonynsimon.cconverter.converter.CurrencyConverter;
import com.anthonynsimon.cconverter.exceptions.InvalidParameterException;
import com.anthonynsimon.cconverter.model.Currency;
import com.anthonynsimon.cconverter.model.ExchangeQuote;
import com.anthonynsimon.cconverter.model.ExchangeRates;
import com.anthonynsimon.cconverter.services.ExchangeRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@Controller
@RequestMapping("/api")
public final class CurrencyExchangeController {

    @Autowired
    ExchangeRateService exchangeRateService;

    @RequestMapping(value = "/convert", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseEntity<ExchangeQuote> convertCurrency(@RequestParam String from,
                                                         @RequestParam String to,
                                                         @RequestParam String amount) throws Exception {
        Currency fromCurrency = new Currency(from);
        Currency toCurrency = new Currency(to);

        if (!fromCurrency.isValid() || !toCurrency.isValid()) {
            throw new InvalidParameterException("invalid 'from' and/or 'to' currencies provided");
        }
        if (amount == null || amount.isEmpty()) {
            throw new InvalidParameterException("invalid amount provided");
        }

        BigDecimal inputAmount;
        try {
            inputAmount = new BigDecimal(amount);
        } catch (NumberFormatException e) {
            throw new InvalidParameterException("invalid amount provided");
        }

        // Handle case when from equals to
        if (fromCurrency.getCurrencyCode().equals(toCurrency.getCurrencyCode())) {
            return new ResponseEntity<>(
                    new ExchangeQuote.Builder()
                            .setAmountToConvert(inputAmount)
                            .setFromCurrency(fromCurrency)
                            .setToCurrency(toCurrency)
                            .setConversionResult(inputAmount)
                            .setExchangeRate(BigDecimal.ONE)
                            .build(),
                    HttpStatus.OK);
        }

        ExchangeRates baseRates = exchangeRateService.getExchangeRates(fromCurrency);
        CurrencyConverter converter = new CurrencyConverter(baseRates);

        return new ResponseEntity<>(
                converter.convert(toCurrency, inputAmount),
                HttpStatus.OK);
    }

    @RequestMapping(value = "/rates/{currency}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<ExchangeRates> getRates(@PathVariable(name = "currency") String currencyCode) throws Exception {
        Currency currency = new Currency(currencyCode);

        if (!currency.isValid()) {
            throw new InvalidParameterException("invalid currency provided");
        }

        return new ResponseEntity<>(
                exchangeRateService.getExchangeRates(currency),
                HttpStatus.OK
        );
    }
}
