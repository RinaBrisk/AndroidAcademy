package ru.androidacademy.msk.NewsApp.ui;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import ru.androidacademy.msk.NewsApp.R;

public class IntroActivity extends AppCompatActivity {

    private static final int LAYOUT = R.layout.activity_intro;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(LAYOUT);
    }
}
