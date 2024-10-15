package avada.spacelab.kino_cms.repository;

import avada.spacelab.kino_cms.model.entity.Auditorium;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

@Repository
public interface AuditoriumRepository extends JpaRepository<Auditorium, Long> {
    @Override @NonNull
    List<Auditorium> findAll();

    @Override
    Optional<Auditorium> findById(Long id);

    @Query("SELECT a FROM Auditorium a JOIN FETCH Theater t on a.theater=t WHERE t.id=:id")
    List<Auditorium> findAuditoriumsByTheaterId(Long id);
}
