package ru.androidacademy.msk.NewsApp.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;

import java.io.IOException;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.androidacademy.msk.NewsApp.DividerNewsItemDecoration;
import ru.androidacademy.msk.NewsApp.State;
import ru.androidacademy.msk.NewsApp.ui.adapter.NewsRecyclerAdapter;
import ru.androidacademy.msk.NewsApp.R;
import ru.androidacademy.msk.NewsApp.network.DefaultResponse;
import ru.androidacademy.msk.NewsApp.network.RestApi;
import ru.androidacademy.msk.NewsApp.network.NewsDTO;


public class NewsListActivity extends AppCompatActivity {

    private static final int LAYOUT = R.layout.activity_news_list;
    private static final String DEFAULT_SEARCH_REQUEST = "home";

    private NewsRecyclerAdapter newsRecyclerAdapter;
    private RecyclerView recyclerView;
    @Nullable
    public Call<DefaultResponse<List<NewsDTO>>> searchRequest;

    private View networkError;
    private Button btnRepeat;
    private ProgressBar progressBar;
    private Button btnNewsCategory;
   // private AlertDialog categoryAlertDialog;

    private final NewsRecyclerAdapter.OnItemClickListener clickListener = new NewsRecyclerAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(@NonNull NewsDTO newsItem) {
            NewsDetailsActivity.startActivity(NewsListActivity.this, newsItem);
        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(LAYOUT);

        findViews();
        btnRepeatSetListener();
        btnNewsCategoryListener();
        setUpRecyclerViewAdapter();
    }

    public void setUpRecyclerViewAdapter(){

        newsRecyclerAdapter = new NewsRecyclerAdapter(this, clickListener, Glide.with(this));
        recyclerView.setAdapter(newsRecyclerAdapter);

        RecyclerView.LayoutManager layoutManager;
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            layoutManager = new LinearLayoutManager(this);
        } else {
            layoutManager = new GridLayoutManager(this, 2);;
        }
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerNewsItemDecoration(getResources().getDimensionPixelSize(R.dimen.divider_news_decoration)));
    }

    public void btnRepeatSetListener(){
        btnRepeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadNews(DEFAULT_SEARCH_REQUEST);
                showState(State.Repeat);
            }
        });
    }

    public void btnNewsCategoryListener(){

        int checkedCategory = 1;
        btnNewsCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogNewsCategory.onCreateAlertDialog(NewsListActivity.this, btnNewsCategory, checkedCategory);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        loadNews(DEFAULT_SEARCH_REQUEST);
    }

    @Override
    public void onStop() {
        super.onStop();
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

    private void loadNews(@NonNull String category) {

        searchRequest = RestApi.getInstance()
                .getNewsEndpoint()
                .search(category);

        progressBar.setVisibility(View.VISIBLE);
        btnNewsCategory.setVisibility(View.INVISIBLE);

        searchRequest.enqueue(new Callback<DefaultResponse<List<NewsDTO>>>() {
            @Override
            public void onResponse(@NonNull Call<DefaultResponse<List<NewsDTO>>> call,
                                   @NonNull Response<DefaultResponse<List<NewsDTO>>> response) {
                showState(State.HasData);
                checkResponseAndSetState(response);
            }

            @Override
            public void onFailure(@NonNull Call<DefaultResponse<List<NewsDTO>>> call,
                                  @NonNull Throwable t) {
                handleError(t);
            }
        });
    }


    private void checkResponseAndSetState(@NonNull Response<DefaultResponse<List<NewsDTO>>> response){

        final DefaultResponse<List<NewsDTO>> body = response.body();

        if(body != null){
            final List<NewsDTO> data =  body.getResults();
            newsRecyclerAdapter.replaceItems(data);
        }
    }

    private void handleError(Throwable throwable) {
        if (throwable instanceof IOException) {
            showState(State.NetworkError);
        }
    }

    public void showState(State state) {

        switch (state) {
            case NetworkError: {

                networkError.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
                btnNewsCategory.setVisibility(View.GONE);
                return;
            }
            case Repeat:{

                networkError.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                return;
            }
            case HasData:{

                progressBar.setVisibility(View.INVISIBLE);
                btnNewsCategory.setVisibility(View.VISIBLE);
                networkError.setVisibility(View.GONE);
            }
        }
    }

    public void findViews(){

        recyclerView = findViewById(R.id.recycler_view);
        networkError = findViewById(R.id.network_error);
        btnRepeat = findViewById(R.id.btn_repeat);
        progressBar = findViewById(R.id.progress_bar);
        btnNewsCategory = findViewById(R.id.btn_news_category);
    }
}

