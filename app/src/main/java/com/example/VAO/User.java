package com.example.VAO;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable,Comparable<User> {
    String email,username,password;
    Boolean admin = false;
    ArrayList<Hotel> favourites;

    public User() {
    }

    public Boolean getAdmin() {
        return admin;
    }

    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }

    public User(String email, String username, String password, Boolean admin) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.admin = admin;
        this.favourites=new ArrayList<Hotel>();
    }

    public ArrayList<Hotel> getFavourites() {
        return favourites;
    }

    public void setFavourites(ArrayList<Hotel> favourites) {
        this.favourites = favourites;
    }

    public User(String email, String username, String password) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.admin=false;
        this.favourites=new ArrayList<Hotel>();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public int compareTo(User o) {
        return this.getUsername().compareTo(o.getUsername());
    }
}
