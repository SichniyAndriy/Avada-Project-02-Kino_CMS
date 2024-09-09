package avada.spacelab.kino_cms.model.mapper;

import avada.spacelab.kino_cms.model.dto.AddressDto;
import avada.spacelab.kino_cms.model.entity.Address;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AddressMapper {
    AddressMapper INSTANCE = Mappers.getMapper(AddressMapper.class);

    AddressDto fromEntityToDto(Address address);
    Address fromDtoToEntity(AddressDto addressDto);
}
