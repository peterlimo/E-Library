package com.example.vlibrary.model;

public class Fetch {
    String title,author,isbN,category,url,id;

    public Fetch(String title, String author, String isbN, String category,String url,String id) {
        this.title = title;
        this.author = author;
        this.isbN = isbN;
        this.category = category;
        this.url =url;
        this.id=id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

