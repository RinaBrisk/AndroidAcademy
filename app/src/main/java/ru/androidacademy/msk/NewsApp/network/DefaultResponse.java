package ru.androidacademy.msk.NewsApp.network;

import com.google.gson.annotations.SerializedName;

public class DefaultResponse<T> {

    @SerializedName("results")
     private T results;

     public T getResults(){
         return results;
     }
}
