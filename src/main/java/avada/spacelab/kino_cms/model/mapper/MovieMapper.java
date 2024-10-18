package avada.spacelab.kino_cms.model.mapper;

import avada.spacelab.kino_cms.model.dto.MovieDto;
import avada.spacelab.kino_cms.model.dto.ScheduleDto;
import avada.spacelab.kino_cms.model.entity.Movie;
import avada.spacelab.kino_cms.model.entity.Schedule;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants.ComponentModel;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = ComponentModel.SPRING)
public interface MovieMapper {
    MovieMapper INSTANCE = Mappers.getMapper(MovieMapper.class);

    @Named("schedulesToScheduleDtos")
    default List<ScheduleDto> schedulesToScheduleDtos(List<Schedule> schedules) {
        List<ScheduleDto> res = new ArrayList<>(schedules.size());
        for (Schedule schedule : schedules) {
            final Long auditoriumId = schedule.getKey().getAuditorium().getId();
            final Long movieId = schedule.getKey().getMovie().getId();
            final LocalDate date = schedule.getKey().getDate();
            final LocalTime time = schedule.getKey().getTime();

            res.add(new ScheduleDto( auditoriumId, movieId, date, time ));
        }
        return res;
    }

    @Mapping(target = "schedules", source = "schedules", qualifiedByName = "schedulesToScheduleDtos")
    MovieDto fromEntityToDto(Movie movie);

    Movie fromDtoToEntity(MovieDto movieDto);
}
