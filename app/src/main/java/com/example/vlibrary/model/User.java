package com.example.vlibrary.model;

public class User {
    String username,email,regNo,password;
    int noOfBooks;
    public User(String username, String email, String regNo, String password,int noOfBooks) {
        this.username = username;
        this.email = email;
        this.regNo = regNo;
        this.password = password;
        this.noOfBooks=noOfBooks;
    }

    public int getNoOfBooks() {
        return noOfBooks;
    }

    public void setNoOfBooks(int noOfBooks) {
        this.noOfBooks = noOfBooks;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRegNo() {
        return regNo;
    }

    public void setRegNo(String regNo) {
        this.regNo = regNo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
