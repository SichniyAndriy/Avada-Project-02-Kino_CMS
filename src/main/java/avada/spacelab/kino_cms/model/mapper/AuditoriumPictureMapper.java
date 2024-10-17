package avada.spacelab.kino_cms.model.mapper;

import avada.spacelab.kino_cms.model.dto.AuditoriumPictureDto;
import avada.spacelab.kino_cms.model.entity.AuditoriumPicture;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants.ComponentModel;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = ComponentModel.SPRING)
public interface AuditoriumPictureMapper {
    AuditoriumPictureMapper INSTANCE = Mappers.getMapper(AuditoriumPictureMapper.class);

    AuditoriumPictureDto fromEntityToDto(AuditoriumPicture auditoriumPicture);

    @Mapping(target = "auditorium", ignore = true)
    AuditoriumPicture fromDtoToEntity(AuditoriumPictureDto auditoriumPictureDto);
}
