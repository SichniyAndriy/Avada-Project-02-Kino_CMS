package avada.spacelab.kino_cms.model.mapper.admin;

import avada.spacelab.kino_cms.model.dto.admin.UserDto;
import avada.spacelab.kino_cms.model.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants.ComponentModel;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = ComponentModel.SPRING)
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDto fromEntityToDto(User user);
    User fromDtoToEntity(UserDto dto);
}
