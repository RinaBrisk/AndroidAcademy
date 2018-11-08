package ru.androidacademy.msk.NewsApp.ui;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.androidacademy.msk.NewsApp.DividerNewsItemDecoration;
import ru.androidacademy.msk.NewsApp.ui.adapter.NewsRecyclerAdapter;
import ru.androidacademy.msk.NewsApp.R;
import ru.androidacademy.msk.NewsApp.State;
import ru.androidacademy.msk.NewsApp.background.BackgroundRunnable;
import ru.androidacademy.msk.NewsApp.background.Utils;
import ru.androidacademy.msk.NewsApp.network.LoadingData;
import ru.androidacademy.msk.NewsApp.network.DefaultResponse;
import ru.androidacademy.msk.NewsApp.network.RestApi;
import ru.androidacademy.msk.NewsApp.network.UserDTO;


public class NewsListActivity extends AppCompatActivity implements LoadingData {

    @NonNull
    private Thread backgroundThread;
    @NonNull
    NewsRecyclerAdapter newsRecyclerAdapter;
    @NonNull
    ProgressBar progressBar;
    @NonNull
    RecyclerView recyclerView;

    @Nullable
    public Call<DefaultResponse<List<UserDTO>>> searchRequest;

    private final NewsRecyclerAdapter.OnItemClickListener clickListener = newsItem -> {
        NewsDetailsActivity.startActivity(this, newsItem);
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_list);

        recyclerView = findViewById(R.id.recycler_view);
        newsRecyclerAdapter = new NewsRecyclerAdapter(this, clickListener);
        recyclerView.setAdapter(newsRecyclerAdapter);

        RecyclerView.LayoutManager layoutManager;
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            layoutManager = new LinearLayoutManager(this);
        } else {
            layoutManager = new GridLayoutManager(this, 2);;
        }
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerNewsItemDecoration(getResources().getDimensionPixelSize(R.dimen.divider_news_decoration)));

        progressBar = findViewById(R.id.progress_bar);
    }

    @Override
    public void onStart() {
        super.onStart();
        backgroundThread = new Thread(new BackgroundRunnable(new Handler(), newsRecyclerAdapter, this));
        backgroundThread.start();
    }

    @Override
    public void onStop() {
        super.onStop();
        if(backgroundThread != null){
            backgroundThread.interrupt();
        }
        backgroundThread = null;
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

    @Override
    public void showProgress(boolean shouldShow) {

        Utils.setVisible(progressBar, shouldShow);
        Utils.setVisible(recyclerView, !shouldShow);
    }

    private void loadNews(@NonNull String search){

        searchRequest = RestApi.getInstance()
                .news()
                .search(search);

        searchRequest.enqueue(new Callback<DefaultResponse<List<UserDTO>>>() {
            @Override
            public void onResponse(@NonNull Call<DefaultResponse<List<UserDTO>>> call,
                                   @NonNull Response<DefaultResponse<List<UserDTO>>> response) {
                checkResponseAndSetState(response);
            }

            @Override
            public void onFailure(@NonNull Call<DefaultResponse<List<UserDTO>>> call,
                                  @NonNull Throwable t) {

            }
        });
    }

    private void checkResponseAndSetState(Response response){

        if(!response.isSuccessful()){

        }
    }

    public void showState(State state){

        switch (state){
            case HasData:{

            }
            case HasNoData:{

            }
            case Loading:{

            }
            case ServerError:{

            }
            case NetworkError:{

            }
        }
    }
}
