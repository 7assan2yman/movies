package com.mycompany.movies;

public class Movie {
    private int id;
    private String title;
    private int year;
    private double rating;
    private int votes;
    private String directors;
    private String stars;
    
    public Movie(int id, String title, int year, double rating, int votes, String directors, String stars) {
        this.id = id;
        this.title = title;
        this.year = year;
        this.rating = rating;
        this.votes = votes;
        this.directors = directors;
        this.stars = stars;
    }
    
    public String getTitle() { return title; }
    public int getYear() { return year; }
    public double getRating() { return rating; }
    public int getVotes() { return votes; }
    public String getDirectors() { return directors; }
    public String getStars() { return stars; }
}