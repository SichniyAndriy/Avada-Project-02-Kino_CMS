package avada.spacelab.kino_cms.repository;

import avada.spacelab.kino_cms.model.entity.Movie;
import java.util.List;
import java.util.Optional;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;

public interface MovieRepository extends JpaRepository<Movie, Long> {

    @Override @NonNull
    List<Movie> findAll();

    @Override
    @NotNull
    Optional<Movie> findById(@NotNull Long id);

    @Query("SELECT m.uaTitle FROM Movie AS m")
    List<String> findAllTitles();

    @Query("SELECT CONCAT(m.has2D, ';', m.has3D, ';', m.hasImax) FROM Movie AS m WHERE m.id=:id AND m.uaTitle=:title")
    String getMovieTypes(Long id, String title);

}
