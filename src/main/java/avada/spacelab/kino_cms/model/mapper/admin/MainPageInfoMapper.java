package avada.spacelab.kino_cms.model.mapper.admin;

import avada.spacelab.kino_cms.model.dto.admin.MainPageInfoDto;
import avada.spacelab.kino_cms.model.entity.MainPageInfo;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants.ComponentModel;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = ComponentModel.SPRING)
public interface MainPageInfoMapper {
    MainPageInfoMapper INSTANCE = Mappers.getMapper(MainPageInfoMapper.class);

    MainPageInfo fromDtoToEntity(MainPageInfoDto mainPageInfoDto);
    MainPageInfoDto fromEntityToDto(MainPageInfo mainPageInfo);
}
