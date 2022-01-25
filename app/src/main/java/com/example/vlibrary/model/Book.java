package com.example.vlibrary.model;

public class Book {
    String title,author,isbN,category,url;

    public Book(String title, String author, String isbN, String category,String url) {
        this.title = title;
        this.author = author;
        this.isbN = isbN;
        this.category = category;
        this.url =url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIsbN() {
        return isbN;
    }

    public void setIsbN(String isbN) {
        this.isbN = isbN;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
