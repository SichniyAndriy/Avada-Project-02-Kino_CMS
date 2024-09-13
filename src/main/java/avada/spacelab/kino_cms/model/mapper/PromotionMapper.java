package avada.spacelab.kino_cms.model.mapper;

import avada.spacelab.kino_cms.model.dto.PromotionDto;
import avada.spacelab.kino_cms.model.entity.Promotion;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PromotionMapper {
    PromotionMapper INSTANCE = Mappers.getMapper(PromotionMapper.class);

    PromotionDto fromEntityToDto(Promotion promotions);
    Promotion fromDtoToEntity(PromotionDto promotionsDto);
}
