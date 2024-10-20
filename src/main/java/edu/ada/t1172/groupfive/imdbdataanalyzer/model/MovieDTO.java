package edu.ada.t1172.groupfive.imdbdataanalyzer.model;

import edu.ada.t1172.groupfive.imdbdataanalyzer.model.enums.Genres;

import java.util.Set;

public record MovieDTO(
        String id,
        String title,
        Set<Genres> genres,
        double averageRating,
        int numVotes,
        int releaseYear
) {
    // Lógica de validação dos dados
    public MovieDTO {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("ID vazio ou nulo.");
        }
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Título vazio ou nulo.");
        }
        if (genres == null || genres.isEmpty()) {
            throw new IllegalArgumentException("Gêneros vazios ou nulos.");
        }
        if (averageRating < 0.0 || averageRating > 10.0) {
            throw new IllegalArgumentException("Avaliação fora do intervalo válido.");
        }
        if (numVotes < 0) {
            throw new IllegalArgumentException("O número de votos não pode ser negativo.");
        }
        int currentYear = java.time.Year.now().getValue();
        if (releaseYear < 1878 || releaseYear > currentYear) {
            throw new IllegalArgumentException("Ano de lançamento fora do intervalo válido.");
        }
    }
}

