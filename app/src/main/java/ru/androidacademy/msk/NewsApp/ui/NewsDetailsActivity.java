package ru.androidacademy.msk.NewsApp.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import ru.androidacademy.msk.NewsApp.R;
import ru.androidacademy.msk.NewsApp.network.NewsDTO;

public class NewsDetailsActivity extends AppCompatActivity {

    private static final String KEY_NEWS_ITEM = "KEY_NEWS_ITEM";
    private static final int LAYOUT = R.layout.activity_news_details;

    private WebView webView;
    private NewsDTO newsDTO;
    private Toolbar toolbar;
    private View progressBar;

    public static void startActivity(@NonNull Context context, @NonNull NewsDTO newsDTO) {
        Intent intent = new Intent(context, NewsDetailsActivity.class);
        intent.putExtra(KEY_NEWS_ITEM, newsDTO);
        context.startActivity(intent);
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(LAYOUT);

        progressBar = findViewById(R.id.progress_bar);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        newsDTO = (NewsDTO) getIntent().getSerializableExtra(KEY_NEWS_ITEM);
        webView = findViewById(R.id.webView);

        // включаем поддержку JavaScript
        /*JavaScript по умолчанию выключен в Webview.
        Следовательно, веб-страницы, содержащие JavaScript
        не будут работать должным образом.*/
        webView.getSettings().setJavaScriptEnabled(true);

        // указываем страницу загрузки
        if(Uri.parse(newsDTO.getUrl()).getHost().length() == 0) {
            return;
        }

        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(newsDTO.getUrl());

        webView.setWebViewClient(new WebViewClient(){

            @Override
            public void onPageFinished(WebView view, String url)
            {
                super.onPageFinished(view, url);
                progressBar.setVisibility(View.INVISIBLE);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                startActivity(new Intent(this, NewsListActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
