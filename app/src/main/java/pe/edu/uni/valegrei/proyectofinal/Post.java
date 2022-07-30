package pe.edu.uni.valegrei.proyectofinal;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.JsonAdapter;

import java.util.Date;

import pe.edu.uni.valegrei.proyectofinal.api.DateTypeAdapter;

public class Post implements Parcelable, Comparable<Post> {
    private static final int SHORT_SIZE = 200;
    private String postId;
    private String postEmail;
    @JsonAdapter(DateTypeAdapter.class)
    private Date postDate;
    private String postTitle;
    private String postBody;
    private String postPhoto;

    public Post(String postEmail, String postTitle, String postBody, String postPhoto) {
        this.postEmail = postEmail;
        this.postTitle = postTitle;
        this.postBody = postBody;
        this.postPhoto = postPhoto;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getPostEmail() {
        return postEmail;
    }

    public void setPostEmail(String postEmail) {
        this.postEmail = postEmail;
    }

    public Date getPostDate() {
        return postDate;
    }

    public void setPostDate(Date postDate) {
        this.postDate = postDate;
    }

    public String getPostTitle() {
        return postTitle;
    }

    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }

    public String getPostBody() {
        return postBody;
    }

    public void setPostBody(String postBody) {
        this.postBody = postBody;
    }

    public String getPostPhoto() {
        return postPhoto;
    }

    public void setPostPhoto(String postPhoto) {
        this.postPhoto = postPhoto;
    }

    public String getShortBody() {
        if (postBody.length() < SHORT_SIZE) {
            return postBody;
        } else {
            return postBody.substring(0, SHORT_SIZE) + "...";
        }
    }

    public boolean hasPostPhoto() {
        return postPhoto != null && !postPhoto.isEmpty();
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.postId);
        dest.writeString(this.postEmail);
        dest.writeLong(this.postDate != null ? this.postDate.getTime() : -1);
        dest.writeString(this.postTitle);
        dest.writeString(this.postBody);
        dest.writeString(this.postPhoto);
    }

    public void readFromParcel(Parcel source) {
        this.postId = source.readString();
        this.postEmail = source.readString();
        long tmpPostDate = source.readLong();
        this.postDate = tmpPostDate == -1 ? null : new Date(tmpPostDate);
        this.postTitle = source.readString();
        this.postBody = source.readString();
        this.postPhoto = source.readString();
    }

    public Post() {
    }

    protected Post(Parcel in) {
        this.postId = in.readString();
        this.postEmail = in.readString();
        long tmpPostDate = in.readLong();
        this.postDate = tmpPostDate == -1 ? null : new Date(tmpPostDate);
        this.postTitle = in.readString();
        this.postBody = in.readString();
        this.postPhoto = in.readString();
    }

    public static final Parcelable.Creator<Post> CREATOR = new Parcelable.Creator<Post>() {
        @Override
        public Post createFromParcel(Parcel source) {
            return new Post(source);
        }

        @Override
        public Post[] newArray(int size) {
            return new Post[size];
        }
    };

    @Override
    public int compareTo(Post o) {
        if (getPostDate() == null || o.getPostDate() == null)
            return 0;
        return -getPostDate().compareTo(o.getPostDate());
    }
}
