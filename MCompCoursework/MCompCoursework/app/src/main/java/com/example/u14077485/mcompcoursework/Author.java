package com.example.u14077485.mcompcoursework;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by u14077485 on 15/03/19.
 */
// Author class for books
public class Author implements Parcelable {
    private String firstName;
    private String lastName;

    public String getFullName() {
        return firstName + " " + lastName;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public Author(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }
    // Parcelable code
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.firstName);
        dest.writeString(this.lastName);
    }

    Author(Parcel in) {
        this.firstName = in.readString();
        this.lastName = in.readString();
    }
    public static final Parcelable.Creator<Author> CREATOR =
            new Parcelable.Creator<Author>() {
                public Author createFromParcel(Parcel in) {
                    return new Author(in);
                }

                public Author[] newArray(int size) {
                    return new Author[size];
                }
            };
}