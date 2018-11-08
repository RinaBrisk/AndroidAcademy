package ru.androidacademy.msk.NewsApp.network;

import java.io.IOException;

import androidx.annotation.NonNull;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class ApiKeyInterceptor implements Interceptor {

    private static final String API_KEY = "api-key";

    private final String apiKey;

    private ApiKeyInterceptor(String apiKey){
        this.apiKey = apiKey;
    }

    public static Interceptor create(@NonNull String apiKey){
        return new ApiKeyInterceptor(apiKey);
    }

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {

        final Request requestWithoutApiKey = chain.request();

        final HttpUrl httpUrl = requestWithoutApiKey.url()
                .newBuilder()
                .addQueryParameter(API_KEY, apiKey)
                .build();

        final Request requestWithAttachedApiKey = requestWithoutApiKey.newBuilder()
                .url(httpUrl)
                .build();

        return chain.proceed(requestWithAttachedApiKey);
    }
}
