package avada.spacelab.kino_cms.model.mapper;

import avada.spacelab.kino_cms.model.dto.PromotionsDto;
import avada.spacelab.kino_cms.model.entity.Promotions;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PromotionMapper {
    PromotionMapper INSTANCE = Mappers.getMapper(PromotionMapper.class);

    PromotionsDto fromEntityToDto(Promotions promotions);
    Promotions fromDtoToEntity(PromotionsDto promotionsDto);
}
