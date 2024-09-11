package avada.spacelab.kino_cms.repository;

import avada.spacelab.kino_cms.model.entity.Auditorium;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuditoriumRepository extends JpaRepository<Auditorium, Long> {
    Auditorium findById(long id);
}
