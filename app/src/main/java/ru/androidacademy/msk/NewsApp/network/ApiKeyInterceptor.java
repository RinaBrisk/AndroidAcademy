package ru.androidacademy.msk.NewsApp.network;

import java.io.IOException;

import androidx.annotation.NonNull;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class ApiKeyInterceptor implements Interceptor {

    private static final String API_KEY = "bf1b28aecc9c45b4b56820cee693e120";
    private static final String API_KEY_NAME = "api-key";

    public static Interceptor create(){
        return new ApiKeyInterceptor();
    }

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {

        final Request requestWithoutApiKey = chain.request();

        final HttpUrl url = requestWithoutApiKey.url()
                .newBuilder()
                .build();

        final Request requestWithAttachedApiKey = requestWithoutApiKey.newBuilder()
                .url(url)
                .addHeader(API_KEY_NAME, API_KEY)
                .build();

        return chain.proceed(requestWithAttachedApiKey);
    }
}
