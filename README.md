# Projeto de Análise de Dados com Programação Funcional em Java - IMDb Top 1000 Movies

Projeto final do módulo de **Programação Funcional em Java**. Trilha **Backend de Java** da **Ada**, Turma #1172, grupo
cinco.
<hr>

## Índice

1. [Descrição do Projeto](#descrição-do-projeto)
2. [Tecnologias Utilizadas](#tecnologias-utilizadas)
3. [Como Executar o Projeto](#como-executar-o-projeto)
4. [Estrutura do Projeto](#estrutura-do-projeto)
    - **Pacote `main`:**
        - [Pacote `app`](#pacote-app)
        - [Pacote `database`](#pacote-database)
        - [Pacote `model`](#pacote-model)
        - [Pacote `utils`](#pacote-util)
        - [Pacote `service`](#pacote-service)
            - [Pacote `questions`](#pacote-questions)
        - [Pacote `dao`](#pacote-dao)
    - **Pacote `test`:**
        - [Pacote `test`](#pacote-test)
5. [Como as Classes Respondem às Perguntas](#como-as-classes-respondem-às-perguntas)
6. [Observações](#observações)
7. [Contribuidores](#contribuidores)

<hr>

## Descrição do Projeto

O objetivo deste projeto é responder a **10 perguntas** utilizando programação funcional, realizando uma análise de
dados. Os dados escolhidos foram os filmes do **IMDb Top 1000 Movies**.

A arquitetura do projeto é baseada em **camadas**, promovendo a separação de responsabilidades e facilitando a
manutenção e escalabilidade do código.

<hr>

## Tecnologias Utilizadas

- **Java**;
- **Maven**;
- **JUnit**;
- **H2 Database**;
- **Hibernate**.

<hr>

## Como Executar o Projeto

Para visualizar a análise, basta executar a classe `Main` localizada no pacote `app`. Os resultados serão impressos no
terminal.

<hr>

## Estrutura do Projeto

### Pacote `app`

Contém a classe `Main`, que inicializa o servidor H2 e chama os métodos dos serviços para responder às perguntas da
análise.

```java
public class Main {
    public static void main(String[] args) {
        H2ServerConfig.start("8181");
        // Chamada aos métodos de análise...
    }
}
```

### Pacote `database`

Contém a classe `H2ServerConfig`, responsável por configurar e iniciar o servidor H2.

```java
public class H2ServerConfig {

    public static void start(String webPort) {
        try {
            Server.createWebServer("-web", "-webAllowOthers", "-webPort", webPort).start();
            System.out.printf("H2 iniciado em http://localhost:%s%n", webPort);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
```

### Pacote `model`

Contém os modelos e enums utilizados no projeto, como a classe `Movie` e o enum `Genres`.

### Pacote `utils`

Contém utilitários que auxiliam na manipulação e análise dos dados:

- `CSVParser`: Responsável por ler e interpretar os dados do arquivo CSV.
- `FileUtils`: Funções utilitárias para manipulação de arquivos.
- `StatisticUtils`: Funções para cálculos estatísticos, como correlação.

### Pacote `service`

Contém a interface `MovieService` e sua implementação `MovieServiceImpl`, que fornecem métodos para manipulação e
análise dos filmes.

### Pacote `questions`

Contém classes que respondem às 10 perguntas propostas no projeto. As perguntas foram divididas em grupos, e cada classe
responde a um grupo específico.

#### Classe `HorrorGenreAnalysis`

Responde às seguintes perguntas:

1. **Existe uma tendência nas avaliações ao longo do tempo para a categoria de filmes de terror?**
2. **Qual o engajamento da audiência para cada gênero?**
3. **Qual a correlação entre engajamento e média de avaliação para cada gênero?**

#### Classe `LotharQuestions`

Responde às perguntas:

4. **Quais são os top 5 filmes de cada categoria no século XX e XXI?**
5. **Quais os 10 filmes mais/menos bem avaliados dentre os filmes com menor quantidade de votos?**

#### Classe `RatingTendencyAnalysis`

Responde às perguntas:

6. **Qual a tendência da avaliação ao longo do tempo?**
7. **Quais os 5 filmes mais "subestimados" e os 5 mais "superestimados"?**

#### Classe `VictorFariaQuestions`

Responde às perguntas:

8. **Qual a correlação entre o número de votos e a média de avaliação para filmes lançados após o ano 2000?**
9. **Quais os gêneros que apresentam maior variação de avaliação entre filmes lançados na década de 1990 e filmes
   lançados na década de 2010?**

#### Classe `VictorFerreiraQuestions`

Responde à pergunta:

10. **Quais os filmes com as maiores discrepâncias entre o número de votos e a média de avaliação, e qual o gênero
    predominante desses filmes?**

### Pacote `dao`

Contém a classe `MovieDAO`, responsável pelo acesso e manipulação dos dados dos filmes no banco de dados.

```java
public class MovieDAO {
    private final CSVParser csvParser;
    private EntityManagerFactory emf;
    private EntityManager em;

    public MovieDAO(CSVParser csvParser) {
        this.csvParser = csvParser;
        try {
            emf = Persistence.createEntityManagerFactory("IMDbData");
            this.em = emf.createEntityManager();
        } catch (Exception e) {
            System.out.println("Erro ao criar EntityManagerFactory: " + e.getMessage());
        }
    }

    public List<Movie> getAllMovies() {
        try {
            return csvParser.getAllMovies();
        } catch (IOException e) {
            throw new CSVParseException("Erro ao ler dados do CSV");
        }
    }

    // Outros métodos para manipulação dos filmes...
}
```

### Pacote `test`

Contém testes unitários para garantir a qualidade e corretude do código nos pacotes `dao`, `service` e `util`.

<hr>

## Como as Classes Respondem às Perguntas

As perguntas foram organizadas conforme as classes responsáveis por respondê-las:

- **Perguntas 1 a 3**: Respondidas pela classe `HorrorGenreAnalysis`.
- **Perguntas 4 e 5**: Respondidas pela classe `LotharQuestions`.
- **Perguntas 6 e 7**: Respondidas pela classe `RatingTendencyAnalysis`.
- **Perguntas 8 e 9**: Respondidas pela classe `VictorFariaQuestions`.
- **Pergunta 10**: Respondida pela classe `VictorFerreiraQuestions`.

<hr>

## Observações

- **Execução da Análise**: Para visualizar a análise, execute a classe `Main` no pacote `app`. Os resultados serão
  exibidos no terminal.
- **Dependências**: Certifique-se de que todas as dependências estão corretamente configuradas no `pom.xml`, incluindo o
  H2 Database e o Hibernate.
- **Banco de Dados**: O projeto utiliza o H2 Database para armazenar os dados dos filmes. O servidor H2 é iniciado
  automaticamente ao executar a classe `Main`.

<hr>

**Desenvolvido por:**

- Grupo 5:
    - [Victor Ferreira](https://github.com/VictorFerreiraS);
    - [Victor Faria](https://github.com/victot-exe);
    - [Lothar Nunnenkamp](https://github.com/LotharNunnenkamp);
    - [Nathan Lucas](https://github.com/nathanllss);
    - [Fernando Archetti](https://github.com/farchettiensis).
- Turma #1172 - Ada, Trilha Backend Java.
- Santander Coders 2024.

---