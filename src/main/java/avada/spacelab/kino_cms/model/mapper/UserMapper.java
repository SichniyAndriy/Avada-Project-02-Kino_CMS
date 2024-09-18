package avada.spacelab.kino_cms.model.mapper;

import avada.spacelab.kino_cms.model.dto.UserDto;
import avada.spacelab.kino_cms.model.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDto fromEntityToDto(User user);
    User fromDtoToEntity(UserDto dto);
}
