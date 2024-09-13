package avada.spacelab.kino_cms.service;

import avada.spacelab.kino_cms.model.dto.PromotionsDto;
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

    public List<PromotionsDto> getAllPromotions() {
        return promotionsRepository.findAll().stream()
                .map(PromotionMapper.INSTANCE::fromEntityToDto)
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
