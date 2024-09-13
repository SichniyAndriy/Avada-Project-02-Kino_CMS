package avada.spacelab.kino_cms;

import avada.spacelab.kino_cms.model.entity.Movie;
import avada.spacelab.kino_cms.model.entity.MovieDetails;
import avada.spacelab.kino_cms.model.entity.News;
import avada.spacelab.kino_cms.model.entity.Promotion;
import avada.spacelab.kino_cms.model.entity.Status;
import avada.spacelab.kino_cms.repository.MovieRepository;
import avada.spacelab.kino_cms.repository.NewsRepository;
import avada.spacelab.kino_cms.repository.PromotionsRepository;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class AppUtil implements CommandLineRunner {
    private final Faker faker = new Faker();
    private final Faker localFaker = new Faker(Locale.getDefault());
    private final MovieRepository movieRepository;
    private final NewsRepository newsRepository;
    private final PromotionsRepository promotionsRepository;

    public AppUtil(
            @Autowired MovieRepository movieRepository,
            @Autowired NewsRepository newsRepository,
            @Autowired PromotionsRepository promotionsRepository
    ) {
        this.movieRepository = movieRepository;
        this.newsRepository = newsRepository;
        this.promotionsRepository = promotionsRepository;
    }
    
    @Override
    public void run(String... args) throws Exception {
        int n = 5;
        initMovies(n * 5);
        initNews(n * 3);
        initPromotions(n * 3);
    }

    private void initMovies(int n) {
        List<Movie> movies = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            Movie movie = new Movie();
            movie.setUaTitle(faker.oscarMovie().movieName());
            movie.setNativeTitle(localFaker.oscarMovie().movieName());
            movie.setDescription(localFaker.lorem().paragraph(localFaker.random().nextInt(4, 8)));
            movie.setHas2D(localFaker.random().nextBoolean());
            movie.setHas3D(localFaker.random().nextBoolean());
            movie.setHasImax(localFaker.random().nextBoolean());
            movie.setTrailerUrl(faker.internet().url());
            movie.setAgeCenz(localFaker.random().nextInt(12, 18));
            MovieDetails movieDetails = new MovieDetails();
            movieDetails.setYear(faker.random().nextInt(2020, LocalDate.now().getYear()).toString());
            movieDetails.setCountry(localFaker.country().name());
            movieDetails.setActors(faker.name().firstName() + " " + faker.name().lastName());
            movieDetails.setDirectors(faker.name().firstName() + " " + faker.name().lastName());
            movieDetails.setScreenwriters(faker.name().firstName() + " " + faker.name().lastName());
            movieDetails.setCompositors(faker.name().firstName() + " " + faker.name().lastName());
            movieDetails.setProducers(faker.name().firstName() + " " + faker.name().lastName());
            movieDetails.setGenres(faker.book().genre());
            movieDetails.setBudget("$ " + faker.random().nextInt(1_000_000, 250_000_000));
            movieDetails.setTime(faker.time().past(7200, ChronoUnit.SECONDS, "HH:mm:ss"));
            movie.setDetails(movieDetails);
            movies.add(movie);
        }
        movieRepository.saveAllAndFlush(movies);
    }

    public void initNews(int n) {
        List<News> news = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            News oneNews = new News();
            oneNews.setTitle(faker.book().title());
            oneNews.setContent(localFaker.lorem().paragraph());
            oneNews.setDate(faker.date().past(30, TimeUnit.DAYS).toLocalDateTime().toLocalDate());
            oneNews.setStatus(faker.random().nextBoolean() ? Status.ON : Status.OFF);
            news.add(oneNews);
        }
        newsRepository.saveAllAndFlush(news);
    }

    public void initPromotions(int n) {
        List<Promotion> promotions = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            Promotion promotion = new Promotion();
            promotion.setTitle(faker.book().title());
            promotion.setContent(localFaker.lorem().paragraph());
            promotion.setDate(faker.date().past(30, TimeUnit.DAYS).toLocalDateTime().toLocalDate());
            promotion.setStatus(faker.random().nextBoolean() ? Status.ON : Status.OFF);
            promotions.add(promotion);
        }
        promotionsRepository.saveAllAndFlush(promotions);
    }
}
