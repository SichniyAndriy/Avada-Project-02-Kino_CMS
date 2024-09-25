package avada.spacelab.kino_cms.repository;

import avada.spacelab.kino_cms.model.entity.Movie;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
    @Override @NonNull
    List<Movie> findAll();

    Movie findMovieById(long id);
}
