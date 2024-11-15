package avada.spacelab.kino_cms.repository;

import avada.spacelab.kino_cms.model.entity.Schedule;
import avada.spacelab.kino_cms.model.entity.Schedule.ScheduleCompositeKey;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ScheduleRepository
        extends JpaRepository<Schedule, ScheduleCompositeKey> {

    @Modifying @Transactional
    @Query("DELETE FROM Schedule s WHERE s.key.auditorium.id=:id")
    void deleteAllByAuditoriumId(@Param("id") Long id);

}
