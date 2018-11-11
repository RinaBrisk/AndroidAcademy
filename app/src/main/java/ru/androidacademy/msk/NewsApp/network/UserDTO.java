package ru.androidacademy.msk.NewsApp.network;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserDTO {

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
    private String publishedDate;
    @SerializedName("multimedia")
    private NewsList<Object> multimedia = null;

    @SerializedName("section")
    public String getSection() {
        return section;
    }

    @SerializedName("subsection")
    public String getSubsection() {
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
    public String getPublishedDate() {
        return publishedDate;
    }

    @SerializedName("multimedia")
    public NewsList<Object> getMultimedia() {
        return multimedia;
    }

}
