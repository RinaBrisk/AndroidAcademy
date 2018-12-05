package ru.androidacademy.msk.NewsApp.network;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class NewsDTO implements Serializable {

    @SerializedName("section")
    private String section;
    @SerializedName("subsection")
    private String subsection;
    @SerializedName("title")
    private String title;
    @SerializedName("abstract")
    private String previewText;
    @SerializedName("url")
    private String url;
    @SerializedName("published_date")
    private Date publishedDate;
    @SerializedName("multimedia")
    private List<Multimedia> multimedia;

    @SerializedName("section")
    public String getSection() {
        return section;
    }

    @SerializedName("subsection")
    public String getCategory() {
        return subsection;
    }

    @SerializedName("title")
    public String getTitle() {
        return title;
    }

    @SerializedName("abstract")
    public String getPreviewText() {
        return previewText;
    }

    @SerializedName("url")
    public String getUrl() {
        return url;
    }

    @SerializedName("published_date")
    public Date getPublishedDate() {
        return publishedDate;
    }

    @SerializedName("multimedia")
    public List<Multimedia> getMultimedia() {
        return multimedia;
    }

}
