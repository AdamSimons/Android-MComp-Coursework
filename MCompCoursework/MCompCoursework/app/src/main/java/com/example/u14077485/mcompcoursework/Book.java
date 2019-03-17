package com.example.u14077485.mcompcoursework;

import java.util.List;

/**
 * Created by u14077485 on 15/03/19.
 */


public class Book {
    private String title;
    private String year;
    private String publisher;
    private List<Author> authors;
    private Double price;


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

    public Book(String title, String year, String publisher, Double price, List<Author> authors) {
        this.title = title;
        this.year = year;
        this.publisher = publisher;
        this.authors = authors;
        this.price = price;
    }
}
