package ru.androidacademy.msk.NewsApp.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import ru.androidacademy.msk.NewsApp.R;
import ru.androidacademy.msk.NewsApp.network.NewsDTO;

public class NewsDetailsActivity extends AppCompatActivity {

    private static final String KEY_NEWS_ITEM = "KEY_NEWS_ITEM";

    public static void startActivity(@NonNull Context context, @NonNull NewsDTO newsItem) {
        Intent intent = new Intent(context, NewsDetailsActivity.class);
        intent.putExtra(KEY_NEWS_ITEM, (Serializable) newsItem);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);

        final ImageView imageView = findViewById(R.id.heading_image_details);
        final TextView title = findViewById(R.id.tv_title_details);
        final TextView publishedDate = findViewById(R.id.tv_published_data_details);
        final TextView fullText = findViewById(R.id.tv_full_text);

        final NewsDTO newsItem = (NewsDTO) getIntent().getSerializableExtra(KEY_NEWS_ITEM);

       // Glide.with(this).load(newsItem.getImageUrl()).into(imageView);
       // title.setText(newsItem.getTitle());
       // publishedDate.setText(new SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault()).format(newsItem.getPublishDate()));
       // fullText.setText(newsItem.getFullText());

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
       //     actionBar.setTitle(newsItem.getCategory().getName());
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
