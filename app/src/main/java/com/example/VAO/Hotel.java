package com.example.VAO;

import java.io.Serializable;
import java.util.ArrayList;

public class Hotel implements Serializable, Comparable<Hotel> {
    String name,picture,description,longitute,latitude;
    ArrayList <Review> reviews = new ArrayList<Review>();

    public ArrayList<Review> getReviews() {
        return this.reviews;
    }

    public void setReviews(ArrayList<Review> reviews) {
        this.reviews = reviews;
    }

    public Hotel() {
    }

    @Override
    public String toString() {
        return "Hotel{" +
                "name='" + name + '\'' +
                ", picture='" + picture + '\'' +
                ", description='" + description + '\'' +
                ", longitute='" + longitute + '\'' +
                ", latitude='" + latitude + '\'' +
                " }";
    }

    public Hotel(String name, String picture, String description, String longitute, String latitude) {
        this.name = name;
        this.picture = picture;
        this.description = description;
        this.longitute = longitute;
        this.latitude = latitude;
    }

    public Hotel(String name, String description, String longitute, String latitude) {
        this.name = name;
        this.description = description;
        this.longitute = longitute;
        this.latitude = latitude;
        this.reviews=new ArrayList<Review>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLongitute() {
        return longitute;
    }

    public void setLongitute(String longitute) {
        this.longitute = longitute;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    //Add review to the list of reviews
    public void addReview (Review review){
        if(this.reviews==null)
            this.reviews= new ArrayList<Review>();
        this.reviews.add(review);
    }

    //Compare hotels by name
    @Override
    public int compareTo(Hotel o) {
        return this.getName().compareTo(o.getName());
    }
}
