package avada.spacelab.kino_cms.service;

import avada.spacelab.kino_cms.model.dto.PromotionDto;
import java.util.List;

public interface PromotionService {
    List<PromotionDto> getAllPromotions();

    PromotionDto getPromotionById(long id);

    void deleteById(long id);

    void save(PromotionDto promotionDto);
}
