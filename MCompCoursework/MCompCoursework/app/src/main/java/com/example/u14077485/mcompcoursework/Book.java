package com.example.u14077485.mcompcoursework;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by u14077485 on 15/03/19.
 */
// Class model
public class Book implements Parcelable {
    private String title;
    private String year;
    private String publisher;
    private List<Author> authors;
    private Double price;
    private String imageURL;
    // Getters and Setters
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getYear() {
        return year;
    }
    public void setYear(String year) {
        this.year = year;
    }
    public String getPublisher() {
        return publisher;
    }
    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }
    public List<Author> getAuthors() {
        return authors;
    }
    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }
    public Double getPrice() {
        return price;
    }
    public void setPrice(Double price) {
        this.price = price;
    }
    public String getImageURL() {
        return imageURL;
    }
    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    // Constructor
    public Book(String title, String year, String publisher, Double price, List<Author> authors, String imageURL) {
        this.title = title;
        this.year = year;
        this.publisher = publisher;
        this.authors = authors;
        this.price = price;
        this.imageURL = imageURL;
    }

    // Used for parcelable
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.year);
        dest.writeString(this.publisher);
        dest.writeDouble(this.price);
        dest.writeList(this.authors);
        dest.writeString(this.imageURL);

    }
    Book (Parcel in) {
        this.title = in.readString();
        this.year = in.readString();
        this.publisher = in.readString();
        this.price = in.readDouble();
        this.authors = (List<Author>) in.readValue(Author.class.getClassLoader());
        this.imageURL = in.readString();
    }
    public static final Parcelable.Creator<Book> CREATOR = new Parcelable.Creator<Book>() {
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        public Book[] newArray(int size) {
            return new Book[size];
        }
    };
}
