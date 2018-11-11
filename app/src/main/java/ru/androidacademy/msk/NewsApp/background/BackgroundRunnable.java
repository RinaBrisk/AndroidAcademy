package ru.androidacademy.msk.NewsApp.background;

import android.os.Handler;

import java.lang.ref.WeakReference;
import java.util.List;

import androidx.annotation.NonNull;
import ru.androidacademy.msk.NewsApp.ui.adapter.NewsRecyclerAdapter;

public class BackgroundRunnable implements Runnable {

    @NonNull
    private final WeakReference<Handler> handlerRef;
    @NonNull
    private final WeakReference<NewsRecyclerAdapter> newsRecyclerAdapterRef;
    @NonNull
    private final WeakReference<LoadingData> loadingDataRef;

    public BackgroundRunnable(@NonNull Handler handler, @NonNull NewsRecyclerAdapter newsRecyclerAdapter, @NonNull LoadingData loadingData) {

        this.handlerRef = new WeakReference<>(handler);
        this.newsRecyclerAdapterRef = new WeakReference<>(newsRecyclerAdapter);
        this.loadingDataRef = new WeakReference<>(loadingData);
    }

    @Override
    public void run() {

        Utils.imitateLoading(2000);

        if (Thread.interrupted()) return;
        Handler handler = handlerRef.get();
        LoadingData loadingData = loadingDataRef.get();
        List<NewsItem> news = DataUtils.generateNews();

        if (handler != null) {
            handler.post(new UIRunnable(news, newsRecyclerAdapterRef, loadingData));
        }
    }
}
