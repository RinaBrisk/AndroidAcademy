package ru.androidacademy.msk.NewsApp.network;

import com.google.gson.annotations.SerializedName;

import android.os.Parcel;
import android.os.Parcelable;

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

//        public NewsDTO(Parcel in) {
//
//            section = in.readString();
//            subsection = in.readString();
//            title = in.readString();
//            previewText = in.readString();
//            url = in.readString();
//            long tmpPublishedDate = in.readLong();
//            publishedDate = tmpPublishedDate != -1 ? new Date(tmpPublishedDate) : null;
//            if (in.readByte() == 0x01) {
//                multimedia = new ArrayList<Multimedia>();
//                in.readList(multimedia, Multimedia.class.getClassLoader());
//            } else {
//                multimedia = null;
//            }
//        }

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

//        @Override
//        public int describeContents() {
//            return 0;
//        }

//    @Override
//    public void writeToParcel(Parcel dest, int flags) {
//        dest.writeStringArray(new String[] {section, subsection, title, previewText, url});
//        dest.writeLong(publishedDate != null ? publishedDate.getTime() : -1L);
//        if (multimedia == null) {
//            dest.writeByte((byte) (0x00));
//        } else {
//            dest.writeByte((byte) (0x01));
//            dest.writeList(multimedia);
//        }
//    }
//
//    public static final Parcelable.Creator<NewsDTO> CREATOR = new Parcelable.Creator<NewsDTO>() {
//
//        @Override
//        public NewsDTO createFromParcel(Parcel in) {
//            return new NewsDTO (in);
//        }
//
//        @Override
//        public NewsDTO[] newArray(int size) {
//            return new NewsDTO[size];
//        }
//    };
}
