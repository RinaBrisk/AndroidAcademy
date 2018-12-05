package ru.androidacademy.msk.NewsApp.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.webkit.WebView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import ru.androidacademy.msk.NewsApp.R;
import ru.androidacademy.msk.NewsApp.network.NewsDTO;

public class NewsDetailsActivity extends AppCompatActivity {

    private static final String KEY_NEWS_ITEM = "KEY_NEWS_ITEM";
    private static final int LAYOUT = R.layout.activity_news_details;

    private WebView webView;

    private NewsDTO newsDTO;

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
        webView.loadUrl(newsDTO.getUrl());

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(newsDTO.getCategory());
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
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
