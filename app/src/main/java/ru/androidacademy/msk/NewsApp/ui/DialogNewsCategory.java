package ru.androidacademy.msk.NewsApp.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

class DialogNewsCategory {

    private static final String[] category ={"Home", "World", "National", "Politics", "Business", "Technology", "Science",
    "Health", "Sports", "Arts", "Books","Movies", "Theater"};

   static int onCreateAlertDialog(Context context, Button btnNewsCategory, int lastCheckedCategory){

       int nowCheckedCategory;

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setSingleChoiceItems(category, lastCheckedCategory, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                btnNewsCategory.setText(category[which]);
                //dialog.cancel();
                dialog.dismiss();
                nowCheckedCategory = which;
            }
        });
       builder.setCancelable(false);

       AlertDialog alert = builder.create();
       alert.show();
       return nowCheckedCategory;
   }
}
