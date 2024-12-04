package avada.spacelab.kino_cms.model.mapper.admin;

import avada.spacelab.kino_cms.model.dto.admin.AuditoriumDto;
import avada.spacelab.kino_cms.model.entity.Auditorium;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants.ComponentModel;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = ComponentModel.SPRING)
public interface AuditoriumMapper {

    AuditoriumMapper INSTANCE = Mappers.getMapper(AuditoriumMapper.class);


    @Mapping(target = "theaterId", source = "theater.id")
    AuditoriumDto fromEntityToDto(Auditorium auditorium);

    @Mapping(target = "theater", ignore = true)
    @Mapping(target = "pictures", ignore = true)
    @Mapping(target = "schedules", ignore = true)
    Auditorium fromDtoToEntity(AuditoriumDto auditoriumDto);

}
