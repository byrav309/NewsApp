package com.exmample.android.newsapp;


public class NewsDetails {

    private String title;
    private String author;
    private String section;
    private String date;

    public NewsDetails(String title, String author, String section, String date){
        this.title = title;
        this.author = author;
        this.section = section;
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getSection() {
        return section;
    }

    public String getDate() {
        return date;
    }
}
