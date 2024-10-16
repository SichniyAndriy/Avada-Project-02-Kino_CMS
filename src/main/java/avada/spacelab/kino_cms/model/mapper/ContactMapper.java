package avada.spacelab.kino_cms.model.mapper;

import avada.spacelab.kino_cms.model.dto.ContactDto;
import avada.spacelab.kino_cms.model.entity.Contact;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants.ComponentModel;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = ComponentModel.SPRING)
public interface ContactMapper {
    ContactMapper INSTANCE = Mappers.getMapper(ContactMapper.class);

    Contact fromDtoToEntity(ContactDto contactDto);
    ContactDto fromEntityToDto(Contact contact);
}
