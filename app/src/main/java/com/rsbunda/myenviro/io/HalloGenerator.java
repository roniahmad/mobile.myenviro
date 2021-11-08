package com.rsbunda.myenviro.io;

import android.content.Context;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.rsbunda.myenviro.util.DateDeserializerUtils;
import com.rsbunda.myenviro.util.TimeDeserializerUtils;

import java.sql.Time;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HalloGenerator {

//    public static final String BASE_URL = "http://192.168.1.138:8002/v1/";

    public static final String BASE_URL = "http://192.168.1.138:8006/";

//    public static final String BASE_URL = "http://192.168.8.108:8006/";
//    public static final String BASE_URL = "https://api.hallobunda.net/v1/";

    private static HttpLoggingInterceptor logging = new HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY);

    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
            .connectTimeout(1, TimeUnit.MINUTES)
            .readTimeout(50, TimeUnit.SECONDS)
            .writeTimeout(50, TimeUnit.SECONDS)
            .addInterceptor(logging);

    private static Gson gson = new GsonBuilder()
//            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .registerTypeAdapter(Date.class, new DateDeserializerUtils())
            .registerTypeAdapter(Time.class, new TimeDeserializerUtils())
            .create();

    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson));

    public static <S> S createService(Class<S> serviceClass, Context context) {
        return createService(serviceClass, null, context);
    }

    public static <S> S createService(Class<S> serviceClass,
                                      final String authToken, final Context context) {
        Retrofit retrofit = builder.build();

        if (!TextUtils.isEmpty(authToken)) {
            AuthorizationInterceptor authorizationInterceptor =
                    new AuthorizationInterceptor (context);

            if(!httpClient.interceptors().contains(authorizationInterceptor)){
                httpClient.addInterceptor(authorizationInterceptor);
            }

            builder.client(httpClient.build());
            retrofit = builder.build();
        }

        return retrofit.create(serviceClass);
    }

    public interface OnConnectionTimeoutListener {
        void onConnectionTimeout();
    }

}
