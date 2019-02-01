package ru.androidacademy.msk.NewsApp.ui;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import ru.androidacademy.msk.NewsApp.FragmentInteraction;
import ru.androidacademy.msk.NewsApp.R;
import ru.androidacademy.msk.NewsApp.network.NewsDTO;

public class NewsDetailsFragment extends Fragment implements FragmentInteraction.OnBackPressListener {

    private static final int LAYOUT = R.layout.fragment_news_details;

    private Unbinder unbinder;
    private static NewsDTO newsDTO;

    @BindView(R.id.webView)      WebView webView;
    @BindView(R.id.progress_bar) View progressBar;
    // private Toolbar toolbar;

    public static NewsDetailsFragment newInstance(NewsDTO news) {
        newsDTO = news;
        return new NewsDetailsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(LAYOUT, container, false);
        unbinder = ButterKnife.bind(this, view);
        createWebView();

        return view;
    }

    //toolbar = findViewById(R.id.toolbar);
    // setSupportActionBar(toolbar);
    // Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

    @Override
    public void onDestroyView() {
        unbinder.unbind();
        super.onDestroyView();
    }

    @Override
    public void onBackPressed() {
        ((MainActivity)Objects.requireNonNull(getActivity())).showNewsList();
    }

    @SuppressLint("SetJavaScriptEnabled")
    public void createWebView() {
        // включаем поддержку JavaScript
        /*JavaScript по умолчанию выключен в Webview.
        Следовательно, веб-страницы, содержащие JavaScript
        не будут работать должным образом.*/
        webView.getSettings().setJavaScriptEnabled(true);

        // указываем страницу загрузки
        if (Uri.parse(newsDTO.getUrl()).getHost().length() == 0) {
            return;
        }

        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(newsDTO.getUrl());

        webView.setWebChromeClient(new WebChromeClient() {

            public void onProgressChanged(WebView view, int progress) {
                if (webView.getProgress() == 100) {
                    //тут получается задержка во времени, и состояние прогресса == 100 происходит
                    // несколько раз, так как некоторые данные догружаются
                    progressBar.setVisibility(View.INVISIBLE);
                }
            }
        });

    }
}
