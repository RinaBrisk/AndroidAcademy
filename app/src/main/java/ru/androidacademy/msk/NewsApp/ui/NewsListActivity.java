package ru.androidacademy.msk.NewsApp.ui;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.bumptech.glide.Glide;

import java.io.IOException;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindInt;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
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

import static ru.androidacademy.msk.NewsApp.ui.adapter.NewsRecyclerAdapter.*;

public class NewsListActivity extends AppCompatActivity {

    private static final int LAYOUT = R.layout.activity_news_list;
    private static final String API_KEY = "y8sH4kt9gZBVJyEoB2DvUHmHyT97zAih";

    private NewsRecyclerAdapter newsRecyclerAdapter;
    private  RecyclerView.LayoutManager layoutManager;

    @Nullable
    public Call<DefaultResponse<List<NewsDTO>>> searchRequest;

    @BindString(R.string.default_search) String sectionSearch;

    @BindView(R.id.recycler_view) RecyclerView recyclerView;
    @BindView(R.id.network_error) View networkError;
    @BindView(R.id.btn_repeat)    Button btnRepeat;
    @BindView(R.id.progress_bar)  ProgressBar progressBar;
    @BindView(R.id.spinner)       Spinner spinner;
    @BindView(R.id.toolbar)       Toolbar toolbar;

    private final OnItemClickListener clickListener = newsDTO -> NewsDetailsActivity.startActivity(NewsListActivity.this, newsDTO);

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(LAYOUT);

        ButterKnife.bind(this);

        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        btnRepeatSetListener();
        setUpRecyclerViewAdapter();
        createSpinner();
    }

    public void setUpRecyclerViewAdapter(){

        newsRecyclerAdapter = new NewsRecyclerAdapter(this, clickListener, Glide.with(this));
        recyclerView.setAdapter(newsRecyclerAdapter);

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
                loadNews(sectionSearch);
                showState(State.Repeat);
            }
        });
    }

    public void createSpinner(){

        final String[] categoriesInRequest = {"home", "world", "national", "politics", "business", "technology", "science",
                "health", "sports", "arts", "books", "movies", "theater"};

        ArrayAdapter<?> adapter =
                ArrayAdapter.createFromResource(this, R.array.newsCategory, android.R.layout.simple_spinner_item);
        // simple_spinner_item - шаблон для представления одного элемента списка
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // simple_spinner_dropdown_item - шаблон для представления раскрывающегося списка
        spinner.setAdapter(adapter);
        spinner.setSelection(0);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int selectedPosition = spinner.getSelectedItemPosition();
                sectionSearch = categoriesInRequest[selectedPosition];
                loadNews(sectionSearch);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });
    }


    @Override
    public void onStart() {
        super.onStart();
        loadNews(sectionSearch);
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        getMenuInflater().inflate(R.menu.menu_about, menu);
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

    public void loadNews(@NonNull String category) {

        searchRequest = RestApi.getInstance()
                .getNewsEndpoint()
                .search(category, API_KEY);

        showState(State.LoadNews);

        searchRequest.enqueue(new Callback<DefaultResponse<List<NewsDTO>>>() {
            @Override
            public void onResponse(@NonNull Call<DefaultResponse<List<NewsDTO>>> call,
                                   @NonNull Response<DefaultResponse<List<NewsDTO>>> response) {
                checkResponseAndSetState(response);
                showState(State.HasData);
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
            layoutManager.scrollToPosition(0);
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
                return;
            }
            case Repeat:{

                networkError.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                return;
            }
            case HasData:{

                progressBar.setVisibility(View.INVISIBLE);
                networkError.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                return;
            }
            case LoadNews:{

                progressBar.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.INVISIBLE);
            }
        }
    }

   /* public void onCreateAlertDialog() {

        final String[] categoriesInDialog = {"Home", "World", "National", "Politics", "Business", "Technology", "Science",
                "Health", "Sports", "Arts", "Books", "Movies", "Theater"};
        final String[] categoriesInRequest = {"home", "world", "national", "politics", "business", "technology", "science",
                "health", "sports", "arts", "books", "movies", "theater"};

        AlertDialog.Builder builder = new AlertDialog.Builder(NewsListActivity.this);
        builder.setSingleChoiceItems(categoriesInDialog,chosenCategory, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                chosenCategory = which;
                loadNews(categoriesInRequest[chosenCategory]);
                dialog.dismiss();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }*/
}

