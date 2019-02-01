package ru.androidacademy.msk.NewsApp.ui.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import okhttp3.internal.Util;
import ru.androidacademy.msk.NewsApp.R;
import ru.androidacademy.msk.NewsApp.Utils;
import ru.androidacademy.msk.NewsApp.network.Multimedia;
import ru.androidacademy.msk.NewsApp.network.NewsDTO;

public class NewsRecyclerAdapter extends RecyclerView.Adapter<NewsRecyclerAdapter.ViewHolder> {

    @NonNull
    private final List<NewsDTO> news = new ArrayList<>();
    @NonNull
    private final LayoutInflater inflater;
    private onClickInterface clickListener;
    private RequestManager glideRequestManager;

    public interface onClickInterface{
        void clicked(NewsDTO newsDTO);
    }

    public void setListener(onClickInterface listener) {
        this.clickListener = listener;
    }

    public NewsRecyclerAdapter(@NonNull Context context, RequestManager glideRequestManager) {
        inflater = LayoutInflater.from(context);
        this.glideRequestManager = glideRequestManager;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        //int viewType - если в Recycler помещаются несколько типов элементов, например реклама
        //тогда реализуется метод GetItemViewType
        return new ViewHolder(
                inflater.inflate(R.layout.news_item, viewGroup, false), glideRequestManager);
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


    public void replaceItems(@NonNull List<NewsDTO> newsDTOS) {
        news.clear();
        news.addAll(newsDTOS);
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public final TextView category;
        public final TextView title;
        public final TextView previewText;
        public final ImageView imageView;
        public final TextView publishedData;
        public final View view;

        private RequestManager imageLoader;

        public ViewHolder(@NonNull View itemView, @NonNull RequestManager glideRequestManager) {
            super(itemView);

            category = this.itemView.findViewById(R.id.tv_category);
            title = this.itemView.findViewById(R.id.tv_title);
            previewText = this.itemView.findViewById(R.id.tv_preview_text);
            imageView = this.itemView.findViewById(R.id.heading_image);
            publishedData = this.itemView.findViewById(R.id.tv_published_data);
            view = itemView;
            this.imageLoader = glideRequestManager;
        }

        void bind(@NonNull NewsDTO newsDTO) {

            view.setOnClickListener(v -> {
                int position = getAdapterPosition();
                //позиция List == AdapterPosition, а позиция на layout  может быть иной
                if (clickListener != null && position != RecyclerView.NO_POSITION) {
                    clickListener.clicked(news.get(position));
                }
            });
            String url;
            if((newsDTO.getMultimedia().size() != 0) && (newsDTO.getMultimedia() != null)) {
                Multimedia multimedia = newsDTO.getMultimedia().get(0);
                url = multimedia.getUrl();

                imageLoader.load(url)
                        .listener(new RequestListener<Drawable>() { //интерфейс для мониторинга статуса запроса, пока идет загрузка изображений
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                return false;
                            }
                        })
                        .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.AUTOMATIC))
                        .thumbnail(0.3f) //установка миниатюры, пока не загрузилось изображение. 0.3% от всего объема изображения - миниатюра
                        .into(imageView);
            }else{
                Utils.setVisible(imageView, false);
            }

            if (newsDTO.getCategory() == null || newsDTO.getCategory().isEmpty()) {
                Utils.setVisible(category, false);
            } else {
                category.setText(newsDTO.getCategory());
            }

            title.setText(newsDTO.getTitle());
            previewText.setText(newsDTO.getPreviewText());
            publishedData.setText(Utils.FormatDateTime(itemView.getContext(), newsDTO.getPublishedDate()));
        }
    }
}
