package com.example.vlibrary.model;

public class Borrow {
    String title,url,isBn,id;

    public Borrow(String title, String url,String isBn,String id) {
        this.title = title;
        this.url = url;
        this.isBn=isBn;
        this.id=id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIsBn() {
        return isBn;
    }

    public void setIsBn(String isBn) {
        this.isBn = isBn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}

