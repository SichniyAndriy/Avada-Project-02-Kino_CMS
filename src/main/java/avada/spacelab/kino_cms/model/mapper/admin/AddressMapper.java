package avada.spacelab.kino_cms.model.mapper.admin;

import avada.spacelab.kino_cms.model.dto.admin.AddressDto;
import avada.spacelab.kino_cms.model.entity.Address;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants.ComponentModel;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = ComponentModel.SPRING)
public interface AddressMapper {
    AddressMapper INSTANCE = Mappers.getMapper(AddressMapper.class);

    AddressDto fromEntityToDto(Address address);
    Address fromDtoToEntity(AddressDto addressDto);
}
