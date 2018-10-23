package ru.androidacademy.msk.NewsApp;

import java.lang.ref.WeakReference;
import java.util.List;

import androidx.annotation.NonNull;

public class UIRunnable implements Runnable {

//    @NonNull public WeakReference<LoadingData> loadingDataRef;

    @NonNull
    private final WeakReference<List> newsRef;
    @NonNull
    private final WeakReference<NewsRecyclerAdapter> newsRecyclerAdapterRef;

    public UIRunnable(@NonNull List<NewsItem> news, @NonNull WeakReference<NewsRecyclerAdapter> newsRecyclerAdapter) {
        //this.loadingDataRef = new WeakReference<>(loadingData);

        this.newsRef = new WeakReference<>(news);
        this.newsRecyclerAdapterRef = newsRecyclerAdapter;
    }

    @Override
    public void run() {
        if(newsRef.get() != null){
            newsRecyclerAdapterRef.get().replaceItems(newsRef.get());
        }
        //LoadingData loadingData = loadingDataRef.get();
        // loadingData.getRecyclerAdapter();
    }
}
