package avada.spacelab.kino_cms.repository;

import avada.spacelab.kino_cms.model.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

    Movie findMovieById(long id);
}
