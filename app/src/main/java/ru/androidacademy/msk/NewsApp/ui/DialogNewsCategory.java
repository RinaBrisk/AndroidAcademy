//package ru.androidacademy.msk.NewsApp.ui;
//
//import android.content.Context;
//import android.content.DialogInterface;
//import android.widget.Button;
//
//import androidx.appcompat.app.AlertDialog;
//import ru.androidacademy.msk.NewsApp.R;
//
//class DialogNewsCategory {
//
//    static void onCreateAlertDialog(Context context, Button btnNewsCategory) {
//
//        AlertDialog.Builder builder = new AlertDialog.Builder(context);
//        builder.setSingleChoiceItems(categoriesInDialog, chosenCategory, new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//
//                btnNewsCategory.setText(categoriesInDialog[which]);
//
//                chosenCategory = which;
//                 builder.dissmis();
//            }
//        });
//        builder.setCancelable(false);
//
//        AlertDialog alert = builder.create();
//        alert.show();
//
//        loadNews(categoriesInRequest[chosenCategory]);
//    }
//}
