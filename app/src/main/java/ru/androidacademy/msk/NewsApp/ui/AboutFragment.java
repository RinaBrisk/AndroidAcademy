package ru.androidacademy.msk.NewsApp.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import butterknife.BindInt;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import ru.androidacademy.msk.NewsApp.FragmentInteraction;
import ru.androidacademy.msk.NewsApp.R;

public class AboutFragment extends Fragment implements FragmentInteraction.OnBackPressListener {

    private Unbinder unbinder;

    @BindString(R.string.vk_url) String VK_URL;
    @BindString(R.string.git_url) String GIT_URL;
    @BindString(R.string.tg_url) String TG_URL;

    @BindView(R.id.et_feedback) TextView feedback_message;
    @BindView(R.id.information) View linear_layout;

    private  static final int LAYOUT =  R.layout.fragment_about;

    public static AboutFragment newInstance(){
        return new AboutFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(LAYOUT, container, false);
        unbinder = ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.btn_vk)
    public void onClickVk(View v) {
       openLink(VK_URL);
    }

    @OnClick(R.id.btn_git)
    public void onClickGit(View v) {
        openLink(GIT_URL);
    }

    @OnClick(R.id.btn_telegram)
    public void onClickTelegram(View v) {
        openLink(TG_URL);
    }

    private void openLink(String URL){
        Uri address = Uri.parse(URL);
        Intent open_link = new Intent(Intent.ACTION_VIEW,address);
        startActivity(open_link);
    }

    @OnClick(R.id.btn_sendMessage)
    public void onClickSendMessage(View v) {
        String message = String.valueOf(feedback_message.getText());
        if(message != null){
            sendMessage(message);
        }
    }

    @Override
    public void onBackPressed() {
        ((MainActivity)Objects.requireNonNull(getActivity())).showNewsList();
    }


    private void sendMessage(String message){

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto: "));     // only email apps should handle this

        String address[] = {(String)getText(R.string.address)};
        intent.putExtra(Intent.EXTRA_EMAIL,address);
        intent.putExtra(Intent.EXTRA_TEXT, message);

        Intent chooser = Intent.createChooser(intent,getText(R.string.chooser));
        if (intent.resolveActivity(Objects.requireNonNull(getContext()).getPackageManager()) != null) {
            startActivity(chooser);
        }
        Toast.makeText(getContext(), getText(R.string.toast), Toast.LENGTH_LONG).show();
    }
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        switch (item.getItemId()) {
//            case android.R.id.home:
//                startActivity(new Intent(this, NewsListFragment.class));
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }

}
