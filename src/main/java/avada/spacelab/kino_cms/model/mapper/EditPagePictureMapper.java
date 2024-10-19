package avada.spacelab.kino_cms.model.mapper;

import avada.spacelab.kino_cms.model.dto.EditPagePictureDto;
import avada.spacelab.kino_cms.model.entity.EditPagePicture;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants.ComponentModel;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = ComponentModel.SPRING)
public interface EditPagePictureMapper {
    EditPagePictureMapper INSTANCE = Mappers.getMapper(EditPagePictureMapper.class);


    EditPagePictureDto fromEntityToDto(EditPagePicture editPagePicture);

    @Mapping(target = "editPage", ignore = true)
    EditPagePicture fromDtoToEntity(EditPagePictureDto editPagePictureDto);
}
