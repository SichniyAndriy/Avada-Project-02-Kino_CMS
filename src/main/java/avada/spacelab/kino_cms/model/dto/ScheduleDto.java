package avada.spacelab.kino_cms.model.dto;

import avada.spacelab.kino_cms.model.entity.Schedule;
import avada.spacelab.kino_cms.model.mapper.AuditoriumMapper;
import avada.spacelab.kino_cms.model.mapper.MovieMapper;
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
    public ScheduleDto(LocalDate date, LocalTime time, MovieDto movie, AuditoriumDto auditorium) {
        this.date = date;
        this.time = time;
        this.movie = movie;
        this.auditorium = auditorium;
    }

    public ScheduleDto(Schedule schedule) {
       this(
               schedule.getKey().getDate(),
               schedule.getKey().getTime(),
               MovieMapper.INSTANCE.fromEntityToDto(schedule.getKey().getMovie()),
               AuditoriumMapper.INSTANCE.fromEntityToDto(schedule.getKey().getAuditorium())
       );
    }
}
