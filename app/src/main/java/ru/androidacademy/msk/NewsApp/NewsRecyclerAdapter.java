package ru.androidacademy.msk.NewsApp;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class NewsRecyclerAdapter extends RecyclerView.Adapter<NewsRecyclerAdapter.ViewHolder> {

    @NonNull private final List<NewsItem> news;
    @NonNull private final Context context;
    @NonNull private final LayoutInflater inflater;

    NewsRecyclerAdapter(@NonNull Context context, @NonNull List<NewsItem> news){
        this.news = news;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        public final TextView category;
        public final TextView title;
        public final TextView previewText;
        public final ImageView imageUrl;
        public final TextView publishedData;

        public ViewHolder(View inflate){
            super(inflate);                  //инициалзация родительского(супер-класса)RecyclerView.ViewHolder
            category = itemView.findViewById(R.id.tv_category);
            title = itemView.findViewById(R.id.tv_title);
            previewText = itemView.findViewById(R.id.tv_preview_text);
            imageUrl = itemView.findViewById(R.id.heading_image);
            publishedData = itemView.findViewById(R.id.tv_published_data);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(
                inflater.inflate(R.layout.recycler_item, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //получить элемент
        NewsItem newsItem = news.get(position);

        //заполнить поля даными
        holder.category.setText(newsItem.getCategory().getName());
        holder.title.setText(newsItem.getTitle());
        holder.previewText.setText(newsItem.getPreviewText());
        Glide.with(context).load(newsItem.getImageUrl()).into(holder.imageUrl);
        holder.publishedData.setText(newsItem.getPublishDate().toString());
    }

    @Override
    public int getItemCount() {
        return news.size();
    }

}
