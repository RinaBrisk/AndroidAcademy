package ru.androidacademy.msk.NewsApp.network;

import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public final class RestApi { // наследование не желательно, поэтому final

    private static final int TIMEOUT_IN_SECONDS = 2;
    private static final String API_KEY = "bf1b28aecc9c45b4b56820cee693e120";
    private static final String URL = "https://api.giphy.com/v1/";

    private static RestApi sRestApi; // singleton class
    private static NewsEndpoint newsEndpoint;

    private RestApi(){

        final OkHttpClient httpClient = buildOkHttpClient();
        final Retrofit retrofitClient = buildRetrofitClient(httpClient);

        newsEndpoint = retrofitClient.create(NewsEndpoint.class);  //объяснили retrofit какой интерфейс реализовывать
    }

    public static synchronized RestApi getInstance(){ // без synchronized только для однопоточных программ
        if(sRestApi == null){
            sRestApi = new RestApi();
        }
        return sRestApi;
    }

    @NonNull
    private Retrofit buildRetrofitClient(@NonNull OkHttpClient okHttpClient){

        return new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @NonNull
    private OkHttpClient buildOkHttpClient(){

        final HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);

        return new OkHttpClient.Builder()
                .addInterceptor(ApiKeyInterceptor.create(API_KEY))
                .addInterceptor(loggingInterceptor)
                .connectTimeout(TIMEOUT_IN_SECONDS, TimeUnit.SECONDS)
                .writeTimeout(TIMEOUT_IN_SECONDS, TimeUnit.SECONDS)
                .readTimeout(TIMEOUT_IN_SECONDS, TimeUnit.SECONDS)
                .build();
    }

    @NonNull
    public NewsEndpoint getNewsEndpoint(){
        return newsEndpoint;
    }

}
