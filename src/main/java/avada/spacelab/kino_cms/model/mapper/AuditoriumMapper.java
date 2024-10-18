package avada.spacelab.kino_cms.model.mapper;

import avada.spacelab.kino_cms.model.dto.AuditoriumDto;
import avada.spacelab.kino_cms.model.entity.Auditorium;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants.ComponentModel;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = ComponentModel.SPRING)
public interface AuditoriumMapper {
    AuditoriumMapper INSTANCE = Mappers.getMapper(AuditoriumMapper.class);

    @AfterMapping
    default void linkingPictures(@MappingTarget Auditorium auditorium) {
        auditorium.getPictures().forEach(picture -> picture.setAuditorium(auditorium));
    }

    AuditoriumDto fromEntityToDto(Auditorium auditorium);

    @Mapping(target = "theater", ignore = true)
    Auditorium fromDtoToEntity(AuditoriumDto auditoriumDto);
}
