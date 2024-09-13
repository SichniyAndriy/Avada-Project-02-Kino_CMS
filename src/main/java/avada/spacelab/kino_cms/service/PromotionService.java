package avada.spacelab.kino_cms.service;

import avada.spacelab.kino_cms.model.dto.PromotionDto;
import avada.spacelab.kino_cms.model.entity.Promotion;
import avada.spacelab.kino_cms.model.entity.SeoBlock;
import avada.spacelab.kino_cms.model.mapper.PromotionMapper;
import avada.spacelab.kino_cms.repository.PromotionsRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PromotionService {
    private final PromotionsRepository promotionsRepository;
    public PromotionService(
            @Autowired PromotionsRepository promotionsRepository
    ) {
        this.promotionsRepository = promotionsRepository;
    }

    public List<PromotionDto> getAllPromotions() {
        return promotionsRepository.findAll().stream()
                .map(PromotionMapper.INSTANCE::fromEntityToDto)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public PromotionDto getPromotionById(int id) {
        Promotion promotionById = promotionsRepository.findById(id);
        if (promotionById.getSeoBlock() == null) {
            promotionById.setSeoBlock(new SeoBlock());
        }
        return PromotionMapper.INSTANCE.fromEntityToDto(promotionById);
    }
}
