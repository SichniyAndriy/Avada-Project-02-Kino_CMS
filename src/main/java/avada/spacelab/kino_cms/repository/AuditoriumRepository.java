package avada.spacelab.kino_cms.repository;

import avada.spacelab.kino_cms.model.entity.Auditorium;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

@Repository
public interface AuditoriumRepository extends JpaRepository<Auditorium, Long> {
    @Override @NonNull
    List<Auditorium> findAll();
}
