package ru.androidacademy.msk.NewsApp.network;

import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public final class RestApi { // наследование не желательно, поэтому final

    private static final int TIMEOUT_IN_SECONDS = 2;
    private static final String URL = "https://api.nytimes.com/svc/"; //протокол / хост /

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
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        return new OkHttpClient.Builder()
                .connectTimeout(TIMEOUT_IN_SECONDS, TimeUnit.SECONDS)
                .addInterceptor(loggingInterceptor)
               // .addInterceptor(ApiKeyInterceptor.create())
                .build();
    }

    @NonNull
    public NewsEndpoint getNewsEndpoint(){
        return newsEndpoint;
    }

}
