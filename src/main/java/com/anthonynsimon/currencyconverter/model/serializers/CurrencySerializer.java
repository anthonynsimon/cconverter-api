package com.anthonynsimon.currencyconverter.model.serializers;

import com.anthonynsimon.currencyconverter.model.Currency;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class CurrencySerializer extends JsonSerializer<Currency> {

    @Override
    public void serialize(Currency value, JsonGenerator jsonGenerator, SerializerProvider provider)
            throws IOException {
        jsonGenerator.writeString(value.getCode());
    }
}
