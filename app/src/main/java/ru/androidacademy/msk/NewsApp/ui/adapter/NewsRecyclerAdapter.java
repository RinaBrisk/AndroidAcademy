package ru.androidacademy.msk.NewsApp.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import ru.androidacademy.msk.NewsApp.background.NewsDTO;
import ru.androidacademy.msk.NewsApp.R;

public class NewsRecyclerAdapter extends RecyclerView.Adapter<NewsRecyclerAdapter.ViewHolder> {

    @NonNull
    private final List<NewsDTO> news = new ArrayList<>();
    @NonNull
    private final LayoutInflater inflater;
    @NonNull
    private final OnItemClickListener clickListener;

    private RequestManager glideRequestManager;

    public NewsRecyclerAdapter(@NonNull Context context, @NonNull OnItemClickListener onItemClickListener, RequestManager glideRequestManager) {

        inflater = LayoutInflater.from(context);
        this.clickListener = onItemClickListener;
        this.glideRequestManager = glideRequestManager;

        //RequestOptions imageOption = new RequestOptions();
       // this.imageLoader = Glide.with(context).applyDefaultRequestOptions(imageOption);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType, RequestManager glideRequestManager) {
        //int viewType - если в Recycler помещаются несколько типов элементов, например реклама
        //тогда реализуется метод GetItemViewType
        return new ViewHolder(
                inflater.inflate(R.layout.news_item, viewGroup, false), clickListener, glideRequestManager);
        //xml элементы одной новости помещаются в ViewGroup
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(news.get(position));
        //получаем позицию новости в List и связываем ее с Recycler. Позиции могут не совпадать
    }

    @Override
    public int getItemCount() {
        return news.size();
    }


    public void replaceItems(@NonNull List<NewsDTO> newsItems) {
        news.clear();
        news.addAll(newsItems);
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onItemClick(@NonNull NewsDTO newsItem);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public final TextView category;
        public final TextView title;
        public final TextView previewText;
        public final ImageView imageView;
        public final TextView publishedData;

        private RequestManager imageLoader;

        public ViewHolder(@NonNull View itemView, @Nullable final OnItemClickListener clickListener, @NonNull RequestManager glideRequestManager) {
            super(itemView);

            itemView.setOnClickListener(newsItem -> {
                //прикрепляем к элементам listener, чтобы он срабатывал при нажатии
                //а реализация onItemClick осуществляется в NewsListActivity
                int position = getAdapterPosition();
                //позиция List == AdapterPosition, а позиция на layout  может быть иной

                if (clickListener != null && position != RecyclerView.NO_POSITION) {
                    clickListener.onItemClick(news.get(position));
                }
            });

            category = this.itemView.findViewById(R.id.tv_category);
            title = this.itemView.findViewById(R.id.tv_title);
            previewText = this.itemView.findViewById(R.id.tv_preview_text);
            imageView = this.itemView.findViewById(R.id.heading_image);
            publishedData = this.itemView.findViewById(R.id.tv_published_data);

            this.imageLoader = glideRequestManager;

        }

        void bind(NewsDTO newsItem) {

           // imageLoader.load(newsItem.getImageUrl()).into(imageView);
            category.setText(newsItem.getCategory().getName());
            title.setText(newsItem.getTitle());
            previewText.setText(newsItem.getPreviewText());
            publishedData.setText(new SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault()).format(newsItem.getPublishDate()));
        }
    }
}
