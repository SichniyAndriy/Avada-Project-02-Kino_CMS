package avada.spacelab.kino_cms.model.mapper;

import avada.spacelab.kino_cms.model.dto.AuditoriumDto;
import avada.spacelab.kino_cms.model.entity.Auditorium;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants.ComponentModel;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = ComponentModel.SPRING)
public interface AuditoriumMapper {
    AuditoriumMapper INSTANCE = Mappers.getMapper(AuditoriumMapper.class);

    AuditoriumDto fromEntityToDto(Auditorium auditorium);
    Auditorium fromDtoToEntity(AuditoriumDto auditoriumDto);
}
