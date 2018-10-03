package ru.androidacademy.msk.NewsApp;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String VK_URL = "https://vk.com/sergeeva_rina7";
    private static final String GIT_URL = "https://github.com/RinaBrisk";
    private static final String TG_URL = "https://web.telegram.org/#/im";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn_vk = findViewById(R.id.btn_vk);
        btn_vk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLinkVk();
            }
        });

        Button btn_git = findViewById(R.id.btn_git);
        btn_git.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLinkGit();
            }
        });

        Button btn_telegram = findViewById(R.id.btn_telegram);
        btn_telegram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLinkTelegram();
            }
        });

        final TextView feedback_message = findViewById(R.id.et_feedback);

        Button btn_sendMessage = findViewById(R.id.btn_sendMessage);
        btn_sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = String.valueOf(feedback_message.getText());
                if(message != null){
                    sendMessage(message);
                }
            }
        });

        View linear_layout = (LinearLayout)findViewById(R.id.information);
        TextView disclaimer = new TextView(this);
        disclaimer.setText(R.string.disclaimer);
        disclaimer.setLayoutParams((new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT)));
        disclaimer.setGravity(Gravity.CENTER_HORIZONTAL);
        ((LinearLayout) linear_layout).addView(disclaimer);
    }

    private void sendMessage(String message){

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto: "));     // only email apps should handle this

        String address[] = {(String)getText(R.string.address)};
        intent.putExtra(Intent.EXTRA_EMAIL,address);
        intent.putExtra(Intent.EXTRA_TEXT, message);

        Intent chooser = Intent.createChooser(intent,getText(R.string.chooser));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(chooser);
        }
        Toast.makeText(this, getText(R.string.toast), Toast.LENGTH_LONG).show();
    }

    private void openLinkVk(){

        Uri address = Uri.parse(VK_URL);
        Intent open_link = new Intent(Intent.ACTION_VIEW,address);
        startActivity(open_link);
    }

    private void openLinkGit(){

        Uri address = Uri.parse(GIT_URL);
        Intent open_link = new Intent(Intent.ACTION_VIEW,address);
        startActivity(open_link);
    }

    private void openLinkTelegram(){

        Uri address = Uri.parse(TG_URL);
        Intent open_link = new Intent(Intent.ACTION_VIEW,address);
        startActivity(open_link);
    }
}
