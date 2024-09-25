package avada.spacelab.kino_cms.model.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * DTO for {@link avada.spacelab.kino_cms.model.entity.Schedule.ScheduleCompositeKey}
 */
public record ScheduleDto(
        LocalDate date,
        LocalTime time,
        MovieDto movie,
        AuditoriumDto auditorium
) implements Serializable {

}
