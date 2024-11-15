package avada.spacelab.kino_cms.model.mapper.admin;

import avada.spacelab.kino_cms.model.dto.admin.PromotionDto;
import avada.spacelab.kino_cms.model.entity.Promotion;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants.ComponentModel;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = ComponentModel.SPRING)
public interface PromotionMapper {
    PromotionMapper INSTANCE = Mappers.getMapper(PromotionMapper.class);

    PromotionDto fromEntityToDto(Promotion promotions);
    Promotion fromDtoToEntity(PromotionDto promotionsDto);
}
