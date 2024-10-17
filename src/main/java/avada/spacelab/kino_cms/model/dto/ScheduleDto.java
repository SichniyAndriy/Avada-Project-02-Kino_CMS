package avada.spacelab.kino_cms.model.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * DTO for {@link avada.spacelab.kino_cms.model.entity.Schedule.ScheduleCompositeKey}
 */
public record ScheduleDto(
        Long auditoriumId,
        Long movieId,
        LocalDate date,
        LocalTime time
) implements Serializable {

}
