package ru.androidacademy.msk.NewsApp;

import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

public class Utils {

    public static void setVisible(@Nullable View view, boolean isVisible){

        int visibility = isVisible ? View.VISIBLE : View.GONE;
        if(view != null) {
            view.setVisibility(visibility);
        }
    }

    public static void imitateLoading(int howLongToWait){

        try {
            Thread.sleep(howLongToWait);
        } catch (InterruptedException e) {
            if(BuildConfig.DEBUG){
                Log.e("Utils", e.getMessage());
            }
        }
    }
}
