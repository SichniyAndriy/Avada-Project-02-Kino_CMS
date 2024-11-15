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

    @Query("SELECT count(m) FROM Movie m")
    long countById();

    @Query("SELECT m.uaTitle FROM Movie AS m")
    List<String> findAllTitles();

}
