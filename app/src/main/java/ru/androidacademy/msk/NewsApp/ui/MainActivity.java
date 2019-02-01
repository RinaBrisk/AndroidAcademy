package ru.androidacademy.msk.NewsApp.ui;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import ru.androidacademy.msk.NewsApp.FragmentInteraction;
import ru.androidacademy.msk.NewsApp.R;
import ru.androidacademy.msk.NewsApp.network.NewsDTO;

public class MainActivity extends AppCompatActivity {

    private static final int LAYOUT = R.layout.activity_main;
    private FragmentInteraction.OnBackPressListener backPressListener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(LAYOUT);
        showNewsList();
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
