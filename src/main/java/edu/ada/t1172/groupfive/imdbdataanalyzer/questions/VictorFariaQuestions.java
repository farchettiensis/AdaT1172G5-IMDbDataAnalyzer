package edu.ada.t1172.groupfive.imdbdataanalyzer.questions;

import edu.ada.t1172.groupfive.imdbdataanalyzer.model.Movie;
import edu.ada.t1172.groupfive.imdbdataanalyzer.model.enums.Genres;
import edu.ada.t1172.groupfive.imdbdataanalyzer.service.MovieService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class VictorFariaQuestions {

    private MovieService service;

    public VictorFariaQuestions(MovieService service) {
        this.service = service;
    }

    public List<Movie> getGenresWithGreatestRatingVariationByDecade(int qntGenres, int decade){

        return service.fetchAllMovies().stream()
                .filter(m -> m.getReleaseYear() >= decade + 9)
                .toList();
    }

    public Double getVoteRatingCorrelationForMoviesAfter(int year){
        List<Movie> filteredMovies = service.fetchAllMovies()
                .stream().filter(m -> m.getReleaseYear() >= year)
                .toList();
        int n = filteredMovies.size();
        if(n == 0) return null;

        double sumVotes = filteredMovies.stream()
                .mapToDouble(Movie::getNumVotes)
                .sum();

        double sumRatings = filteredMovies.stream()
                .mapToDouble(Movie::getAverageRating)
                .sum();

        double sumVotesSquared = filteredMovies.stream()
                .mapToDouble(m -> Math.pow(m.getNumVotes(), 2))
                .sum();

        double sumRatingsSquared = filteredMovies.stream()
                .mapToDouble(m -> Math.pow(m.getAverageRating(), 2))
                .sum();

        double sumProduct = filteredMovies.stream()
                .mapToDouble(m -> m.getNumVotes() * m.getAverageRating())
                .sum();

        double numerator = sumProduct - (sumVotes * sumRatings / n);
        double denominator = Math.sqrt((sumVotesSquared - Math.pow(sumVotes, 2) / n) *
                (sumRatingsSquared - Math.pow(sumRatings, 2) / n));

        return denominator == 0 ? null : numerator / denominator;
    }


}
/*
 double denominator = Math.sqrt((sumVotesSq - Math.pow(sumVotes, 2) / n) * (sumRatingsSq - Math.pow(sumRatings, 2) / n));
1. Definir o que é correlação:
Correlação é uma medida estatística que indica o grau de associação entre duas variáveis. Varia de -1 (correlação negativa perfeita) a +1 (correlação positiva perfeita), com 0 indicando nenhuma correlação.
2. Coleta dos dados relevantes:
Você precisará de dados de filmes lançados após o ano 2000.
Esses dados devem incluir:
Número de votos (exemplo: total de pessoas que avaliaram cada filme).
Média de avaliação (exemplo: a nota média dada ao filme).
Um banco de dados de filmes como IMDb, TMDB, ou um dataset específico pode fornecer essas informações.
3. Filtrar filmes lançados após 2000:
Certifique-se de que os dados que você está utilizando incluem apenas filmes lançados após o ano 2000.
Pode ser necessário aplicar uma filtragem por data de lançamento.
4. Calcular a correlação:
Utilize uma função estatística para calcular a correlação entre o número de votos e a média de avaliação dos filmes.
Ferramentas ou bibliotecas de programação como Pandas (Python) ou Excel possuem funções para calcular correlação.
Por exemplo, em Python com Pandas: df['num_votes'].corr(df['rating'])
O resultado será um número entre -1 e 1:
Correlação positiva (próxima de +1) significa que, conforme o número de votos aumenta, a média de avaliação também tende a aumentar.
Correlação negativa (próxima de -1) significa que, conforme o número de votos aumenta, a média de avaliação tende a diminuir.
Correlação próxima de 0 indica que não há relação clara entre o número de votos e a média de avaliação.
5. Interpretar o resultado:
Alta correlação positiva: indica que filmes com mais votos tendem a ter uma avaliação mais alta (ou seja, quanto mais pessoas votam, maior a nota média).
Alta correlação negativa: indica que filmes com mais votos tendem a ser avaliados mais negativamente.
Correlação baixa ou próxima de zero: sugere que o número de votos não tem muita influência direta sobre a avaliação média.
6. Considerar outliers ou padrões adicionais:
Após calcular a correlação, pode ser útil verificar se há outliers (filmes muito populares ou impopulares) que estão influenciando o resultado.
Além disso, você pode dividir os dados em categorias como gênero, ano específico, ou país, para ver se a correlação muda em diferentes contextos.
Seguindo esse raciocínio, você conseguirá calcular e interpretar a correlação entre o número de votos e a média de avaliação para filmes lançados após 2000.
 */
// 1980 -> 1989
