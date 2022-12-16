package movies;

import java.util.ArrayList;

public class Movie {
    private String name;
    private int year;
    private int duration;
    private ArrayList<String> genres;
    private ArrayList<String> actors;

    private int numLikes = 0;

    private float rating = 0;

    private int numRatings = 0;

    public ArrayList<Float> getRatingsList() {
        return ratingsList;
    }

    private ArrayList<Float> ratingsList;

    public int getNumLikes() {
        return numLikes;
    }

    public void increaseNumLikes() {
        this.numLikes = this.numLikes + 1;
    }

    /**
     *
     * @param rate to add and increase number of ratings
     */
    public void increaseNumRatings(final float rate) {
        this.numRatings = this.numRatings + 1;
        if (getRatingsList() == null) {
            ratingsList = new ArrayList<>();
        }
        getRatingsList().add(rate);
    }

    /**
     *
     * @param rate to add and calculate again rating for movie
     */
    public void calculateRating(final float rate) {
        increaseNumRatings(rate);
        float sum = 0;
        for (float r : ratingsList) {
            sum += r;
        }
        rating = sum / ratingsList.size();
    }

    /**
     *
     * @param numLikes for movie
     */
    public void setNumLikes(final int numLikes) {
        this.numLikes = numLikes;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public int getNumRatings() {
        return numRatings;
    }

    public void setNumRatings(final int numRatings) {
        this.numRatings = numRatings;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public int getYear() {
        return year;
    }

    public void setYear(final int year) {
        this.year = year;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(final int duration) {
        this.duration = duration;
    }

    public ArrayList<String> getGenres() {
        return genres;
    }

    public void setGenres(final ArrayList<String> genres) {
        this.genres = genres;
    }

    public ArrayList<String> getActors() {
        return actors;
    }

    public void setActors(final ArrayList<String> actors) {
        this.actors = actors;
    }

    public ArrayList<String> getCountriesBanned() {
        return countriesBanned;
    }

    public void setCountriesBanned(final ArrayList<String> countriesBanned) {
        this.countriesBanned = countriesBanned;
    }

    private ArrayList<String> countriesBanned;
}
