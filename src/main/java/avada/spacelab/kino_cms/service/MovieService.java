package avada.spacelab.kino_cms.service;

import avada.spacelab.kino_cms.model.entity.Movie;
import avada.spacelab.kino_cms.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MovieService {
    MovieRepository movieRepository;

    public MovieService(
            @Autowired MovieRepository movieRepository
    ) {
        this.movieRepository = movieRepository;
    }


    public Movie getMovieById(int id) {
        return movieRepository.findMovieById(id);
    }
}
