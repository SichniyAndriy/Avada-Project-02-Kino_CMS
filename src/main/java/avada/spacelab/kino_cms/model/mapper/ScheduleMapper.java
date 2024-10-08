package avada.spacelab.kino_cms.model.mapper;

import avada.spacelab.kino_cms.model.dto.ScheduleDto;
import avada.spacelab.kino_cms.model.entity.Schedule;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ScheduleMapper {
    ScheduleMapper INSTANCE = Mappers.getMapper(ScheduleMapper.class);

    ScheduleDto fromEntityToDto(Schedule schedule);
    Schedule fromDtoToEntity(ScheduleDto scheduleDto);
}
