package edu.ada.t1172.groupfive.imdbdataanalyzer.questions;

import edu.ada.t1172.groupfive.imdbdataanalyzer.model.Movie;
import edu.ada.t1172.groupfive.imdbdataanalyzer.model.enums.Genres;
import edu.ada.t1172.groupfive.imdbdataanalyzer.service.MovieService;

public class VictorFerreiraQuestions {
    MovieService service;

    public VictorFerreiraQuestions(MovieService service) {
        this.service = service;
    }

    public Movie questionOne(){
        return service.getTopRatedMovie(service.getMoviesByGenre(service.fetchAllMovies(), Genres.DRAMA));
    }
}
