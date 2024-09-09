package avada.spacelab.kino_cms;

import avada.spacelab.kino_cms.model.entity.Movie;
import avada.spacelab.kino_cms.repository.MovieRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class AppUtil implements CommandLineRunner {
    private final Faker faker = new Faker();
    private final Faker localFaker = new Faker(Locale.getDefault());
    private final MovieRepository movieRepository;

    public AppUtil(
            @Autowired MovieRepository movieRepository
    ) {
        this.movieRepository = movieRepository;
    }
    
    @Override
    public void run(String... args) throws Exception {
        int n = 5;
        initMovies(n * 5);
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
            movies.add(movie);
        }
        movieRepository.saveAllAndFlush(movies);
    }
}
