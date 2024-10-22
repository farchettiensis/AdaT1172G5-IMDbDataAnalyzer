package edu.ada.t1172.groupfive.imdbdataanalyzer.model;

import edu.ada.t1172.groupfive.imdbdataanalyzer.model.enums.Genres;

import java.util.Objects;
import java.util.Set;

public class Movie extends BaseModel {
    private String id;
    private String title;
    private Set<Genres> genres;
    private double averageRating;
    private int numVotes;
    private int releaseYear;

    public Movie() {
    }

    public Movie(String id, String title, Set<Genres> genres, double averageRating, int numVotes, int releaseYear) {
        this.id = id;
        this.title = title;
        this.genres = genres;
        this.averageRating = averageRating;
        this.numVotes = numVotes;
        this.releaseYear = releaseYear;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Set<Genres> getGenres() {
        return genres;
    }


    public double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(double averageRating) {
        this.averageRating = averageRating;
    }

    public int getNumVotes() {
        return numVotes;
    }

    public void setNumVotes(int numVotes) {
        this.numVotes = numVotes;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return Objects.equals(id, movie.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return String.format("Title: %-66s Genres: %-35s Average rating: %-5s Number of votes: %-10s Release year: %s", title, genres, averageRating, numVotes, releaseYear);
    }
}
