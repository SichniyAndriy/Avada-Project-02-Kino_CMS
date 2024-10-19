package avada.spacelab.kino_cms.model.mapper;

import avada.spacelab.kino_cms.model.dto.EditPageDto;
import avada.spacelab.kino_cms.model.entity.EditPage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants.ComponentModel;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = ComponentModel.SPRING)
public interface EditPageMapper {
    EditPageMapper INSTANCE = Mappers.getMapper(EditPageMapper.class);


    EditPageDto fromEntityToDto(EditPage editPage);

    @Mapping(target = "pictures",ignore = true)
    EditPage fromDtoToEntity(EditPageDto editPageDto);
}