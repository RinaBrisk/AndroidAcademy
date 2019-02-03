package ru.androidacademy.msk.NewsApp.ui;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
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

public class NewsListFragment extends Fragment implements NewsRecyclerAdapter.onClickInterface{

    private NewsRecyclerAdapter newsRecyclerAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private Unbinder unbinder;

    @Nullable
    public Call<DefaultResponse<List<NewsDTO>>> searchRequest;
    private  static final int LAYOUT = R.layout.fragment_news_list;

    @BindString(R.string.default_search) String sectionSearch;
    @BindString(R.string.api_key) String API_KEY;

    @BindView(R.id.recycler_view) RecyclerView recyclerView;
    @BindView(R.id.network_error) View networkError;
    @BindView(R.id.progress_bar)  ProgressBar progressBar;
    //@BindView(R.id.spinner)       Spinner spinner;

    public static NewsListFragment newInstance() {
        return new NewsListFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //сделать то, что не связано с интерфейсом
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(LAYOUT, container, false);
        unbinder = ButterKnife.bind(this, view);

        setUpRecyclerViewAdapter();
       // createSpinner();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //вызывается, когда отработает метод активности onCreate(), а значит фрагмент может обратиться к компонентам активности
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
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @OnClick(R.id.btn_repeat)
    public void onClickBtnRepeat(View v) {
        loadNews(sectionSearch);
        showState(State.Repeat);
    }

//    public void createSpinner(){
////
////        final String[] categoriesInRequest = {"home", "world", "national", "politics", "business", "technology", "science",
////                "health", "sports", "arts", "books", "movies", "theater"};
////
////        ArrayAdapter<?> adapter =
////                ArrayAdapter.createFromResource(Objects.requireNonNull(getContext()), R.array.newsCategory, android.R.layout.simple_spinner_item);
////        // simple_spinner_item - шаблон для представления одного элемента списка
////        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
////        // simple_spinner_dropdown_item - шаблон для представления раскрывающегося списка
////        spinner.setAdapter(adapter);
////        spinner.setSelection(0);
////
////        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
////
////            @Override
////            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
////                int selectedPosition = spinner.getSelectedItemPosition();
////                sectionSearch = categoriesInRequest[selectedPosition];
////                loadNews(sectionSearch);
////            }
////            @Override
////            public void onNothingSelected(AdapterView<?> parent) {
////            }
////        });
////    }

    public void setUpRecyclerViewAdapter(){

        newsRecyclerAdapter = new NewsRecyclerAdapter(Objects.requireNonNull(getActivity()), Glide.with(getActivity()));
        newsRecyclerAdapter.setListener(this);
        recyclerView.setAdapter(newsRecyclerAdapter);

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            layoutManager = new LinearLayoutManager(getActivity());
        } else {
            layoutManager = new GridLayoutManager(getActivity(), 2);;
        }
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerNewsItemDecoration(getResources().getDimensionPixelSize(R.dimen.divider_news_decoration)));
    }

    @Override
    public void clicked(@NonNull NewsDTO newsDTO) {
        ((MainActivity)Objects.requireNonNull(getActivity())).showDetails(newsDTO);
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

        AlertDialog.Builder builder = new AlertDialog.Builder(NewsListFragment.this);
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

