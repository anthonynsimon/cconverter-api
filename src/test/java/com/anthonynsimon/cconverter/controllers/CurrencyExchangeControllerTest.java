package com.anthonynsimon.cconverter.controllers;

import com.anthonynsimon.cconverter.model.Currency;
import com.anthonynsimon.cconverter.model.ExchangeRates;
import com.anthonynsimon.cconverter.providers.ExchangeRateProvider;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CurrencyExchangeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    @Qualifier("fixer.io")
    private ExchangeRateProvider exchangeRateProvider;

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
    public void testConvertMissingParams() throws Exception {
        this.mockMvc.perform(post("/api/convert")
                .param("from", "JPY")
                .param("to", "")
                .param("amount", "1.0"))
                .andExpect(status().is4xxClientError());

        this.mockMvc.perform(post("/api/convert")
                .param("from", "")
                .param("to", "USD")
                .param("amount", "1.0"))
                .andExpect(status().is4xxClientError());

        this.mockMvc.perform(post("/api/convert")
                .param("bad", "EUR")
                .param("to", "USD")
                .param("amount", "1.0"))
                .andExpect(status().is4xxClientError());

        this.mockMvc.perform(post("/api/convert")
                .param("from", "JPY")
                .param("to", "USD"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void testConvertNonNumericAmount() throws Exception {
        this.mockMvc.perform(post("/api/convert")
                .param("from", "JPY")
                .param("to", "USD")
                .param("amount", "1.0AB"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void testInvalidCurrency() throws Exception {
        this.mockMvc.perform(post("/api/convert")
                .param("from", "CRC")
                .param("to", "USD")
                .param("amount", "1.0AB"))
                .andExpect(status().is4xxClientError());

        this.mockMvc.perform(post("/api/convert")
                .param("from", "USDD")
                .param("to", "USD")
                .param("amount", "1.0AB"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void testOneToOne() throws Exception {
        this.mockMvc.perform(post("/api/convert")
                .param("from", "USD")
                .param("to", "USD")
                .param("amount", "500"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().string(containsString("\"exchangeRate\":1")));

        this.mockMvc.perform(post("/api/convert")
                .param("from", "JPY")
                .param("to", "JPY")
                .param("amount", "500"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().string(containsString("\"exchangeRate\":1")));
    }

    @Test
    public void testConvert() throws Exception {
        this.mockMvc.perform(post("/api/convert")
                .param("from", "USD")
                .param("to", "EUR")
                .param("amount", "1.0"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().string(containsString("conversionResult")));

        this.mockMvc.perform(post("/api/convert")
                .param("from", "JPY")
                .param("to", "USD")
                .param("amount", "1.0"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().string(containsString("conversionResult")));
    }

    @Test
    public void testCaseInsensible() throws Exception {
        this.mockMvc.perform(post("/api/convert")
                .param("from", "jpy")
                .param("to", "EUR")
                .param("amount", "1.0"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().string(containsString("conversionResult")));

        this.mockMvc.perform(post("/api/convert")
                .param("from", "eur")
                .param("to", "uSd")
                .param("amount", "1.0"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().string(containsString("conversionResult")));
    }

    @Test
    public void testGetRates() throws Exception {
        this.mockMvc.perform(get("/api/rates/{currency}", "EUR"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().string(containsString("base")));
    }

    @Test
    public void testGetRatesBadInput() throws Exception {
        this.mockMvc.perform(get("/api/rates/{currency}", ""))
                .andExpect(status().is4xxClientError());

        this.mockMvc.perform(get("/api/rates/{currency}", "EURO"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void testNotFoundRoute() throws Exception {
        this.mockMvc.perform(get("/api/rates/{currency}/something", "EUR"))
                .andExpect(status().isNotFound());

        this.mockMvc.perform(get("/api/rate/{currency}", "EUR"))
                .andExpect(status().isNotFound());

        this.mockMvc.perform(get("/convert", "EUR"))
                .andExpect(status().isNotFound());

        this.mockMvc.perform(get("/api/converts", "EUR"))
                .andExpect(status().isNotFound());
    }
}
