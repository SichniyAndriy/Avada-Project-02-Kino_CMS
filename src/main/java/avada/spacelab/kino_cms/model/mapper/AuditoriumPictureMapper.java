package avada.spacelab.kino_cms.model.mapper;

import avada.spacelab.kino_cms.model.dto.AuditoriumPictureDto;
import avada.spacelab.kino_cms.model.entity.AuditoriumPicture;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AuditoriumPictureMapper {
    AuditoriumPictureMapper INSTANCE = Mappers.getMapper(AuditoriumPictureMapper.class);

    AuditoriumPictureDto fromEntityToDto(AuditoriumPicture auditoriumPicture);
    AuditoriumPicture fromDtoToEntity(AuditoriumPictureDto auditoriumPictureDto);
}
