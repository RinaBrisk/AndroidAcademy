package ru.androidacademy.msk.NewsApp;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class NewsListActivity extends AppCompatActivity {

    private final NewsRecyclerAdapter.OnItemClickListener clickListener = newsItem -> {
        NewsDetailsActivity.startActivity(this, newsItem);
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_list);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setAdapter(new NewsRecyclerAdapter(this, DataUtils.generateNews(), clickListener));

        RecyclerView.LayoutManager layoutManager;
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            layoutManager = new LinearLayoutManager(this);
        } else {
            layoutManager = new GridLayoutManager(this, 2);;
        }
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.addItemDecoration(new DividerNewsItemDecoration(getResources().getDimensionPixelSize(R.dimen.divider_news_decoration)));
    }

    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        getMenuInflater().inflate(R.menu.menu_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_about:
                startActivity(new Intent(this, AboutActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
