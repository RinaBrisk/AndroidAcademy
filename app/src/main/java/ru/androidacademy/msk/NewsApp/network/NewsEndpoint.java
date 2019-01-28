package ru.androidacademy.msk.NewsApp.network;

import java.util.List;

import androidx.annotation.NonNull;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface NewsEndpoint {
    @NonNull
    @GET("topstories/v2/{section}.json")
    Call<DefaultResponse<List<NewsDTO>>> search(@Path("section") String category, @Query("api-key") String api_key);
}
