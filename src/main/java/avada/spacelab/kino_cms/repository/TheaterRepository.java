package avada.spacelab.kino_cms.repository;

import avada.spacelab.kino_cms.model.entity.Theater;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

@Repository
public interface TheaterRepository extends JpaRepository<Theater, Long> {
    @Override @NonNull
    List<Theater> findAll();

    @Override
    Optional<Theater> findById(Long aLong);

    @Query("SELECT t from Theater t JOIN FETCH Auditorium a ON t=a.theater WHERE a.id=:id")
    Theater findTheaterByIdAuditorium(Long id);
}
