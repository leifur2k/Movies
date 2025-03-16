package com.leif2k.movies.movie_pojo;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@Entity(tableName = "favourite_movies")
public class Movie implements Serializable {

    @PrimaryKey
    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("year")
    private int year;

    @SerializedName("description")
    private String description;

    @Embedded
    @SerializedName("rating")
    private Rating rating;

    @Embedded
    @SerializedName("poster")
    private Poster poster;


    public Movie(int id, String name, int year, String description, Rating rating, Poster poster) {
        this.id = id;
        this.name = name;
        this.year = year;
        this.description = description;
        this.rating = rating;
        this.poster = poster;
    }


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getYear() {
        return year;
    }

    public String getDescription() {
        return description;
    }

    public Rating getRating() {
        return rating;
    }

    public Poster getPoster() {
        return poster;
    }


    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", year=" + year +
                ", rating=" + rating +
                ", poster=" + poster +
                '}';
    }
}
