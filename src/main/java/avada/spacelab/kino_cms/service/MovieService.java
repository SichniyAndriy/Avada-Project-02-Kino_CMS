package avada.spacelab.kino_cms.service;

import avada.spacelab.kino_cms.model.dto.MovieDto;
import avada.spacelab.kino_cms.model.mapper.MovieMapper;
import avada.spacelab.kino_cms.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MovieService {
    private MovieRepository movieRepository;

    public MovieService(
            @Autowired MovieRepository movieRepository
    ) {
        this.movieRepository = movieRepository;
    }

    public MovieDto getMovieById(long id) {
        return MovieMapper.INSTANCE.fromEntityToDto(movieRepository.findMovieById(id));
    }
}
