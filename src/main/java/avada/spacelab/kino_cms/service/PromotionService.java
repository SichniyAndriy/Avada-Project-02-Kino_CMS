package avada.spacelab.kino_cms.service;

import avada.spacelab.kino_cms.model.dto.PromotionDto;
import avada.spacelab.kino_cms.model.entity.Promotion;
import avada.spacelab.kino_cms.model.entity.SeoBlock;
import avada.spacelab.kino_cms.model.mapper.PromotionMapper;
import avada.spacelab.kino_cms.repository.PromotionRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PromotionService {
    private final PromotionRepository promotionRepository;

    public PromotionService(
            @Autowired PromotionRepository promotionRepository
    ) {
        this.promotionRepository = promotionRepository;
    }

    public List<PromotionDto> getAllPromotions() {
        return promotionRepository.findAll().stream()
                .map(PromotionMapper.INSTANCE::fromEntityToDto)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public PromotionDto getPromotionById(long id) {
        Optional<Promotion> promotionById = promotionRepository.findById(id);
        if (promotionById.isPresent()) {
            Promotion promotion = promotionById.get();
            if (promotion.getSeoBlock() == null) {
                promotion.setSeoBlock(new SeoBlock());
            }
            return PromotionMapper.INSTANCE.fromEntityToDto(promotion);
        }
        return PromotionDto.EMPTY();
    }

    public void deleteById(long id) {
        promotionRepository.deleteById(id);
    }

    public void save(PromotionDto promotionDto) {
        Promotion promotion = PromotionMapper.INSTANCE.fromDtoToEntity(promotionDto);
        promotionRepository.save(promotion);
    }
}
