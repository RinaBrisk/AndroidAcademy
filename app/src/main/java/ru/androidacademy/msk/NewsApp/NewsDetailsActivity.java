package ru.androidacademy.msk.NewsApp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class NewsDetailsActivity extends AppCompatActivity {

    private static final String KEY_IMAGE = "KEY_IMAGE";
    private static final String KEY_TITLE = "KEY_TITLE";
    private static final String KEY_DATE = "KEY_DATE";
    private static final String KEY_FULLTEXT = "KEY_FULLTEXT";
    private static final String KEY_CATEGORY = "KEY_CATEGORY";

    public ImageView imageView;
    public TextView title;
    public TextView publishedDate;
    public TextView fullText;

    static public Intent createIntent(Context context, String imageURL, String title, String publishedDate, String fullText, String category) {
        Intent intent = new Intent(context, NewsDetailsActivity.class);
        intent.putExtra(KEY_IMAGE, imageURL).
                putExtra(KEY_TITLE, title).
                putExtra(KEY_DATE, publishedDate).
                putExtra(KEY_FULLTEXT, fullText).
                putExtra(KEY_CATEGORY, category);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);

        imageView = findViewById(R.id.heading_image_details);
        title = findViewById(R.id.tv_title_details);
        publishedDate = findViewById(R.id.tv_published_data_details);
        fullText = findViewById(R.id.tv_full_text);

        Intent intent = getIntent();
        Glide.with(this).load(intent.getStringExtra(KEY_IMAGE)).into(imageView);
        title.setText(intent.getStringExtra(KEY_TITLE));

        // в коде ошибка, неизвестно как исправить
        //SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault());
        //String date = simpleDateFormat.format(intent.getStringExtra(KEY_DATE));
        //publishedDate.setText(date);
        publishedDate.setText(intent.getStringExtra(KEY_DATE));
        fullText.setText(intent.getStringExtra(KEY_FULLTEXT));

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(intent.getStringExtra(KEY_CATEGORY));
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
