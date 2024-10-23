package avada.spacelab.kino_cms.repository;

import avada.spacelab.kino_cms.model.entity.Auditorium;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

@Repository
public interface AuditoriumRepository extends JpaRepository<Auditorium, Long> {
    @Override @NonNull
    List<Auditorium> findAll();

    @Override @NonNull
    Optional<Auditorium> findById(@NonNull Long id);

    @Query("SELECT a FROM Auditorium a JOIN FETCH Theater t on a.theater=t WHERE t.id=:id")
    List<Auditorium> findAuditoriumsByTheaterId(@Param("id") Long id);

    @Modifying @Transactional
    @Query("DELETE FROM Auditorium a WHERE a.theater.id=:id")
    void deleteByTheaterId(@Param("id") long id);
}
