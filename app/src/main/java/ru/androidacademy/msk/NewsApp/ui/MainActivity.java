package ru.androidacademy.msk.NewsApp.ui;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import ru.androidacademy.msk.NewsApp.FragmentInteraction;
import ru.androidacademy.msk.NewsApp.R;
import ru.androidacademy.msk.NewsApp.network.NewsDTO;

public class MainActivity extends AppCompatActivity {

    private static final int LAYOUT = R.layout.activity_main;
    private FragmentInteraction.OnBackPressListener backPressListener;
    @BindView(R.id.toolbar) Toolbar toolbar;
  //  @BindView(R.id.spinner) Spinner spinner;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(LAYOUT);

        ButterKnife.bind(this);

        if (toolbar != null) {
            setSupportActionBar(toolbar);
            toolbar.setTitle("");
            Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }
        showNewsList();
    }

    @Override
    public boolean onSupportNavigateUp() {
        backPressListener.onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        if (fragment instanceof FragmentInteraction.OnBackPressListener) {
            backPressListener = (FragmentInteraction.OnBackPressListener) fragment;
        }
    }

    @Override
    public void onBackPressed() {
        backPressListener.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        getMenuInflater().inflate(R.menu.menu_about, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
        //    case R.id.home:
        //        backPressListener.onBackPressed();
        //        return true;
            case R.id.action_about:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.activity_news_frame, AboutFragment.newInstance())
                        .commit();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void showDetails(NewsDTO newsDTO){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.activity_news_frame, NewsDetailsFragment.newInstance(newsDTO))
                .commit();
    }

    public void showNewsList(){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.activity_news_frame, NewsListFragment.newInstance())
                .commit();
    }
}
