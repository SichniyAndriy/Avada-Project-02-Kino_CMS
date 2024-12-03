package avada.spacelab.kino_cms.repository;

import avada.spacelab.kino_cms.model.entity.Theater;
import java.util.List;
import java.util.Optional;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;

public interface TheaterRepository extends JpaRepository<Theater, Long> {

    @Override @NonNull
    List<Theater> findAll();

    @Query("SELECT CONCAT(t.id, ';', IFNULL(t.title, 'empty'), ';', IFNULL(t.logoUrl, 'empty')) FROM Theater AS t")
    List<String> findTheaterResponceDtos();

    @Override
    @NotNull
    Optional<Theater> findById(@NotNull Long aLong);

    @Query("SELECT t from Theater t JOIN FETCH Auditorium a ON t=a.theater WHERE a.id=:id")
    Theater findTheaterByIdAuditorium(@Param("id") Long id);

    @Query("SELECT t.title FROM Theater AS t")
    List<String> findAllTitles();

    @Override
    void deleteById(@NotNull Long id);

}
