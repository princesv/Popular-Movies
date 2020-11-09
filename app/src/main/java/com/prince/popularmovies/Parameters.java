package com.prince.popularmovies;

import java.util.List;

public class Parameters {
    List<String> movieName;
    List<Double> movieRating;
    List<String> movieImage;

    public List<String> getMovieName() {
        return movieName;
    }

    public void setMovieName(List<String> movieName) {
        this.movieName = movieName;
    }


    public List<Double> getMovieRating() {
        return movieRating;
    }

    public void setMovieRating(List<Double> movieRating) {
        this.movieRating = movieRating;
    }

    public List<String> getMovieImage() {
        return movieImage;
    }

    public void setMovieImage(List<String> movieImage) {
        this.movieImage = movieImage;
    }
}
