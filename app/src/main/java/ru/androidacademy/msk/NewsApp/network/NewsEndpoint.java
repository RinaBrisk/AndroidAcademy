package ru.androidacademy.msk.NewsApp.network;

import java.util.List;

import androidx.annotation.NonNull;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface NewsEndpoint {
    @NonNull
    @GET("gifs/search")
    Call<DefaultResponse<List<UserDTO>>> search(@Url String url);
}
