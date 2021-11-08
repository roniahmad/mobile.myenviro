package com.rsbunda.myenviro.util;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import static com.rsbunda.myenviro.util.LogUtils.makeLogTag;

public class TimeDeserializerUtils implements JsonDeserializer<Time>  {

    private static final String TAG = makeLogTag(TimeDeserializerUtils.class);

    private static final String TIME_FORMAT = "HH:mm:ss";

    @Override
    public Time deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {

        try {
            String s = json.getAsString();
            SimpleDateFormat sdf = new SimpleDateFormat(TIME_FORMAT, Locale.US);
            sdf.parse(s);
            long ms = sdf.parse(s).getTime();
            Time t = new Time(ms);
            return t;
        } catch (ParseException e) {
        }

        throw new JsonParseException("Unparseable time: \"" + json.getAsString()
                + "\". Supported formats: " + TIME_FORMAT);
    }
}
