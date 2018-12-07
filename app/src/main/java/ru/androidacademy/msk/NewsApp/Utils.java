package ru.androidacademy.msk.NewsApp;

import android.content.Context;
import android.provider.ContactsContract;
import android.text.format.DateUtils;
import android.view.View;

import java.util.Date;

import androidx.annotation.Nullable;

public class Utils {

    public static CharSequence FormatDateTime (Context context, Date date){

        return DateUtils.getRelativeDateTimeString( // возвращает дату в формате "[relative time/date], [time]"
                // показывает разницу во времени между введенной датой и настоящим временем
                context,
                date.getTime(),
                DateUtils.MINUTE_IN_MILLIS, //мин.разница в часах. Например 23 сек назад будут показаны как "0 минут назад",
                DateUtils.WEEK_IN_MILLIS,// еденица времени по прошествии которого прекращается отчетность
                DateUtils.FORMAT_ABBREV_RELATIVE //будет показывать в виде  "42 mins ago".
        );
    }

    public static void setVisible(@Nullable View view, Boolean isVisible){
        if(view == null)
            return;

        if(isVisible){
            view.setVisibility(View.GONE);
        }

        if(!isVisible){
            view.setVisibility(View.GONE);
        }
    }
}
