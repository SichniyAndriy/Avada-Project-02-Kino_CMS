package avada.spacelab.kino_cms.model.mapper;

import avada.spacelab.kino_cms.model.dto.AuditoriumDto;
import avada.spacelab.kino_cms.model.entity.Auditorium;
import org.mapstruct.factory.Mappers;

public interface AuditoriumMapper {
    AuditoriumMapper ISTANCE = Mappers.getMapper(AuditoriumMapper.class);

    AuditoriumDto fromEntityToDto(Auditorium auditorium);
    Auditorium fromDtoToEntity(AuditoriumDto auditoriumDto);
}
