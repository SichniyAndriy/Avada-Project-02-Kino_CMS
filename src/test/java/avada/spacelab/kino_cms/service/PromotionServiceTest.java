package avada.spacelab.kino_cms.service;

import avada.spacelab.kino_cms.model.dto.PromotionDto;
import avada.spacelab.kino_cms.model.entity.Promotion;
import avada.spacelab.kino_cms.model.entity.SeoBlock;
import avada.spacelab.kino_cms.repository.PromotionRepository;
import avada.spacelab.kino_cms.service.impl.PromotionServiceImpl;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicNode;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PromotionServiceTest {

    @Mock
    private PromotionRepository promotionRepository;
    @InjectMocks
    private PromotionServiceImpl promotionService;


    @TestFactory
    @DisplayName("Test getAllPromotions()")
    List<DynamicNode> test_getAllPromotions() {
        return List.of(
                dynamicTest(
                        "With valid list",
                        () -> {
                            final int SIZE = 3;
                            List<Promotion> promotions = new ArrayList<>(SIZE);
                            for (int i = 1; i <= SIZE; ++i) {
                                promotions.add(getPromotion(i));
                            }

                            when(promotionRepository.findAll()).thenReturn(promotions);

                            List<PromotionDto> result = promotionService.getAllPromotions();

                            assertEquals(SIZE, result.size());
                            verify(promotionRepository).findAll();
                        }
                ),dynamicTest(
                        "With empty list",
                        () -> {
                            when(promotionRepository.findAll()).thenReturn(Collections.emptyList());

                            List<PromotionDto> result = promotionService.getAllPromotions();

                            assertEquals(0, result.size());
                            verify(promotionRepository, times(2)).findAll();
                        }
                ), dynamicTest(
                        "With null list",
                        () -> {
                            when(promotionRepository.findAll()).thenReturn(null);

                            assertThrows(
                                    NullPointerException.class,
                                    () -> promotionService.getAllPromotions()
                            );
                            verify(promotionRepository, times(3)).findAll();
                        }
                )
        );
    }

    @TestFactory
    @DisplayName("Test getPromotionById()")
    List<DynamicNode> test_getPromotionById() {
        return List.of(
                dynamicTest(
                        "With valid id and empty SeoBlock",
                        () -> {
                            when(promotionRepository.findById(anyLong()))
                                    .thenReturn(Optional.of(getPromotion(1L)));

                            PromotionDto result = promotionService.getPromotionById(1L);

                            assertEquals(1L, result.id());
                            assertNotNull(result.seoBlock());
                            verify(promotionRepository, times(1)).findById(anyLong());
                        }
                ),
                dynamicTest(
                        "With valid id and not empty SeoBlock",
                        () -> {
                            Promotion promotion = getPromotion(1L);
                            promotion.setSeoBlock(new SeoBlock());
                            when(promotionRepository.findById(anyLong()))
                                    .thenReturn(Optional.of(promotion));

                            PromotionDto result = promotionService.getPromotionById(1L);

                            assertEquals(1L, result.id());
                            assertNotNull(result.seoBlock());
                            verify(promotionRepository,times(2)).findById(anyLong());
                        }
                ),
                dynamicTest(
                        "With invalid id",
                        () -> {
                            when(promotionRepository.findById(anyLong()))
                                    .thenReturn(Optional.empty());

                            PromotionDto promotionById = promotionService.getPromotionById(0);

                            assertNull(promotionById.id());
                            verify(promotionRepository, times(3)).findById(anyLong());
                        }
                )
        );
    }

    @Test
    @DisplayName("Test deleteById()")
    void test_deleteById() {
        doNothing().when(promotionRepository).deleteById(anyLong());
        promotionService.deleteById(1L);
        verify(promotionRepository, times(1)).deleteById(anyLong());
    }

    @TestFactory
    @DisplayName("Test save()")
    List<DynamicNode> test_save() {
        return List.of(
                dynamicTest(
                        "With valid parameter",
                        () -> {
                            when(promotionRepository.save(any(Promotion.class)))
                                    .thenReturn(getPromotion(1));

                            promotionService.save(PromotionDto.EMPTY());

                            verify(promotionRepository, times(1))
                                    .save(any(Promotion.class));
                        }
                ),
                dynamicTest(
                        "With null parameter",
                        () -> {
                            when(promotionRepository.save(isNull())).thenReturn(null);

                            promotionService.save(null);

                            verify(promotionRepository, times(1))
                                    .save(isNull());
                        }
                )
        );
    }

    private Promotion getPromotion(long id) {
        Promotion promotion = new Promotion();
        promotion.setId(id);
        return promotion;
    }

}
