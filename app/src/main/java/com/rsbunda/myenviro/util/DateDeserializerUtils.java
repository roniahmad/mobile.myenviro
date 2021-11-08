package com.rsbunda.myenviro.util;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import static com.rsbunda.myenviro.util.LogUtils.LOGE;
import static com.rsbunda.myenviro.util.LogUtils.makeLogTag;

public class DateDeserializerUtils implements JsonDeserializer<Date> {
    private static final String TAG = makeLogTag(DateDeserializerUtils.class);

    @Override
    public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        String date = json.getAsString();

//        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd"); // HH:mm:ss
        formatter.setTimeZone(TimeZone.getTimeZone("U TC"));

        try {
            return formatter.parse(date);
        } catch (ParseException e) {
            LOGE(TAG, e.getMessage());
            return null;
        }
    }
}
