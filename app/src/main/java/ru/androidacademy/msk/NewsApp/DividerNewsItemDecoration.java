package ru.androidacademy.msk.NewsApp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DividerNewsItemDecoration extends RecyclerView.ItemDecoration {


    private final int offset;

    public DividerNewsItemDecoration(int offset) {
        this.offset = offset;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent,
                               @NonNull RecyclerView.State state) {

        final int position = parent.getChildLayoutPosition(view);

        if (position != RecyclerView.NO_POSITION) {
            outRect.set(offset, offset, offset, offset);
        } else {
            outRect.set(0, 0, 0, 0);
        }
    }
}

