package ru.androidacademy.msk.NewsApp.background;

import java.lang.ref.WeakReference;
import java.util.List;

import androidx.annotation.NonNull;
import ru.androidacademy.msk.NewsApp.ui.adapter.NewsRecyclerAdapter;

public class UIRunnable implements Runnable {

    @NonNull
    private final WeakReference<List> newsRef;
    @NonNull
    private final WeakReference<NewsRecyclerAdapter> newsRecyclerAdapterRef;
    @NonNull
    private final WeakReference<LoadingData> loadingDataRef;

    public UIRunnable(@NonNull List<NewsDTO> news, @NonNull WeakReference<NewsRecyclerAdapter> newsRecyclerAdapter, LoadingData loadingData) {

        this.newsRef = new WeakReference<>(news);
        this.newsRecyclerAdapterRef = newsRecyclerAdapter;
        this.loadingDataRef = new WeakReference<>(loadingData);
    }

    @Override
    public void run() {
        if(newsRef.get() != null){
            newsRecyclerAdapterRef.get().replaceItems(newsRef.get());
        }
        loadingDataRef.get().showProgress(false);
    }
}
