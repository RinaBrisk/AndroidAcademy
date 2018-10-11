package ru.androidacademy.msk.NewsApp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

public class NewsRecyclerAdapter extends RecyclerView.Adapter<NewsRecyclerAdapter.ViewHolder> {

    @NonNull
    private final List<NewsItem> news;
    @NonNull
    private final LayoutInflater inflater;
    @NonNull
    private final OnItemClickListener clickListener;
    @NonNull
    private final RequestManager imageLoader;

    NewsRecyclerAdapter(@NonNull Context context, @NonNull List<NewsItem> news, @NonNull OnItemClickListener onItemClickListener) {
        this.news = news;
        inflater = LayoutInflater.from(context);
        this.clickListener = onItemClickListener;

        RequestOptions imageOption = new RequestOptions();
        this.imageLoader = Glide.with(context).applyDefaultRequestOptions(imageOption);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(
                inflater.inflate(R.layout.news_item, parent, false), clickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(news.get(position));
    }

    @Override
    public int getItemCount() {
        return news.size();
    }


    public interface OnItemClickListener {
        void onItemClick(NewsItem newsItem);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public final TextView category;
        public final TextView title;
        public final TextView previewText;
        public final ImageView imageView;
        public final TextView publishedData;

        public ViewHolder(@NonNull View itemView, @Nullable final OnItemClickListener clickListener) {
            super(itemView);

            itemView.setOnClickListener(newsItem -> {
                int position = getAdapterPosition();
                if (clickListener != null && position != RecyclerView.NO_POSITION) {
                    clickListener.onItemClick(news.get(position));
                }
            });

            category = this.itemView.findViewById(R.id.tv_category);
            title = this.itemView.findViewById(R.id.tv_title);
            previewText = this.itemView.findViewById(R.id.tv_preview_text);
            imageView = this.itemView.findViewById(R.id.heading_image);
            publishedData = this.itemView.findViewById(R.id.tv_published_data);
        }

        void bind(NewsItem newsItem) {

            imageLoader.load(newsItem.getImageUrl()).into(imageView);
            category.setText(newsItem.getCategory().getName());
            title.setText(newsItem.getTitle());
            previewText.setText(newsItem.getPreviewText());
            publishedData.setText(newsItem.getPublishDate().toString());
        }
    }
}
