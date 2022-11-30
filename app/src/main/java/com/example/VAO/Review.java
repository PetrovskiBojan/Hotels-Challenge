package com.example.VAO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;

public class Review implements Serializable, Comparable<Review>
{
    Hotel hotel;
    String author;
    String description;
    int rating;
    ArrayList <User> likes;
    ArrayList<User> dislikes;

    public Review(){}

    public Review(Hotel hotel, String author, String description, int rating) {
        this.hotel = hotel;
        this.author = author;
        this.description = description;
        this.rating = rating;
        this.likes= new ArrayList<User>();
        this.dislikes= new ArrayList<User>();
    }

    @Override
    public String toString() {
        return "Review{" +
                ", author='" + author + '\'' +
                ", description='" + description + '\'' +
                ", rating=" + rating +
                '}';
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public ArrayList<User> getLikes() {
        return likes;
    }

    public void setLikes(ArrayList<User> likes) {
        this.likes = likes;
    }

    public ArrayList<User> getDislikes() {
        return dislikes;
    }

    public void setDislikes(ArrayList<User> dislikes) {
        this.dislikes = dislikes;
    }

    //Compare reviews by name
    @Override
    public int compareTo(Review o) {
        return this.hotel.getName().compareTo(o.getHotel().getName());
    }
}
