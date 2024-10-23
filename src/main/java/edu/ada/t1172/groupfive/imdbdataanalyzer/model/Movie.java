package edu.ada.t1172.groupfive.imdbdataanalyzer.model;

import edu.ada.t1172.groupfive.imdbdataanalyzer.model.enums.Genres;
import jakarta.persistence.*;

import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "movies")
public class Movie extends BaseModel {
    @Id
    private String id;
    private String title;

    @ElementCollection
    @JoinTable(name = "movie_genres", joinColumns = @JoinColumn(name = "movie_id"))
    @Enumerated(EnumType.STRING)
    private Set<Genres> genres;
    private double averageRating;
    private int numVotes;
    private int releaseYear;
    @Column(name = "genres")
    private String genresString;

    public Movie() {
    }

    public Movie(String id, String title, Set<Genres> genres, double averageRating, int numVotes, int releaseYear) {
        this.id = id;
        this.title = title;
        this.genres = genres;
        this.averageRating = averageRating;
        this.numVotes = numVotes;
        this.releaseYear = releaseYear;
        this.genresString = genreConverter(genres);
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

    public void setGenres(Set<Genres> genres) {
        this.genres = genres;
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

    public String getGenresString() {
        return genresString;
    }

    public void setGenresString(String genresString) {
        this.genresString = genresString;
    }

    private String genreConverter(Set<Genres> genresList) {
        if (genres == null) {
            return null;
        }
        return String.join(", ", genresList.stream().map(Genres::toString).toArray(String[]::new));
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
        return String.format("Title: %-66s Genre: %-35s Average rating: %-5s Number of votes: %-10s Release year: %s", title, genres, averageRating, numVotes, releaseYear);
    }
}
