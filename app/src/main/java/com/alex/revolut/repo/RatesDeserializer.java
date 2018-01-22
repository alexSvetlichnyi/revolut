package com.alex.revolut.repo;

import com.alex.revolut.model.Rate;
import com.alex.revolut.model.Rates;
import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class RatesDeserializer implements JsonDeserializer<Rates> {
    @Override
    public Rates deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws
            JsonParseException {
        Gson gson = new Gson();
        Rates response = gson.fromJson(jsonElement, type);
        List<Rate> rates = new ArrayList<>();

        Set<Map.Entry<String, JsonElement>> entries = jsonElement.getAsJsonObject().get("rates").getAsJsonObject().entrySet();
        Rate rate = new Rate();
        rate.setCode(response.getBase());
        rate.setValue(1);
        rates.add(rate);

        for (Map.Entry<String, JsonElement> entry : entries) {
            rate = new Rate();
            rate.setCode(entry.getKey());
            rate.setValue(entry.getValue().getAsDouble());
            rates.add(rate);
        }
        response.setRates(rates);

        return response;
    }
}
