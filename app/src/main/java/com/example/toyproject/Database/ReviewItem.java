package com.example.toyproject.Database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;

import java.util.List;

@Entity(tableName = "items")
public class ReviewItem {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    private long key;
    private long author;
    private String title;
    private String comment;
    private float rating;
    private double longitude;
    private double latitude;
    @TypeConverters(UriConverter.class)
    private List<String> images;

    public long getKey() {
        return key;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setKey(long key) {
        this.key = key;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public List<String> getImages() {
        return images;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(long author) {
        this.author = author;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public long getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public String getComment() {
        return comment;
    }

    public float getRating() {
        return rating;
    }
}
