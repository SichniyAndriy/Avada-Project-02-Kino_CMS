package avada.spacelab.kino_cms.model.mapper.admin;

import avada.spacelab.kino_cms.model.dto.admin.ScheduleDto;
import avada.spacelab.kino_cms.model.entity.Schedule;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants.ComponentModel;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = ComponentModel.SPRING)
public interface ScheduleMapper {

    ScheduleMapper INSTANCE = Mappers.getMapper(ScheduleMapper.class);


    @Mapping(target = "auditoriumId", source = "key.auditorium.id")
    @Mapping(target = "movieId", source = "key.movie.id")
    @Mapping(target = "date", source = "key.date")
    @Mapping(target = "time", source = "key.time" )
    ScheduleDto fromEntityToDto(Schedule schedule);

}
