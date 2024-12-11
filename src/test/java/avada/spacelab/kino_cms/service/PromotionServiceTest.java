package avada.spacelab.kino_cms.service;

import avada.spacelab.kino_cms.model.dto.admin.PromotionDto;
import avada.spacelab.kino_cms.model.entity.Promotion;
import avada.spacelab.kino_cms.model.entity.SeoBlock;
import avada.spacelab.kino_cms.model.entity.Status;
import avada.spacelab.kino_cms.model.mapper.admin.PromotionMapper;
import avada.spacelab.kino_cms.repository.PromotionRepository;
import avada.spacelab.kino_cms.service.admin.impl.PromotionServiceImpl;
import java.time.LocalDate;
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
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PromotionServiceTest {

    @Mock
    private PromotionRepository promotionRepository;
    @InjectMocks
    private PromotionServiceImpl promotionService;

    private final long ID = 1L;


    @TestFactory
    @DisplayName("Test getAllPromotions()")
    List<DynamicNode> test_getAllPromotions() {
        return List.of(
                dynamicTest(
                        "With valid list",
                        () -> {
                            final int SIZE = 3;
                            List<Promotion> promotions = new ArrayList<>(SIZE);
                            for (long i = 1; i <= SIZE; ++i) {
                                promotions.add(getPromotion(i, true));
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
                                    .thenReturn(Optional.of(getPromotion(1L, true)));

                            PromotionDto result = promotionService.getPromotionById(1L);

                            assertEquals(1L, result.id());
                            assertNotNull(result.seoBlock());
                            verify(promotionRepository, times(1)).findById(anyLong());
                        }
                ),
                dynamicTest(
                        "With valid id and not empty SeoBlock",
                        () -> {
                            Promotion promotion = getPromotion(1L, true);
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
                        "With valid parameters",
                        () -> {
                            when(promotionRepository.save(any(Promotion.class)))
                                    .thenReturn(getPromotion(1, true));

                            promotionService.save(getPromotionDto(ID, true));

                            verify(promotionRepository, times(1))
                                    .save(any(Promotion.class));
                        }
                ),
                 dynamicTest(
                        "With valid parameters and null date",
                        () -> {
                            when(promotionRepository.save(any(Promotion.class)))
                                    .thenReturn(getPromotion(1, false));

                            promotionService.save(getPromotionDto(ID, false));

                            verify(promotionRepository, times(2))
                                    .save(any(Promotion.class));
                        }
                ),
                dynamicTest(
                        "With null parameter",
                        () -> {
                            assertThrows(
                                    NullPointerException.class,
                                    () -> promotionService.save(null)
                            );
                            verify(promotionRepository, never())
                                    .save(isNull());
                        }
                )
        );
    }

     @Test
    @DisplayName("test getActiveNews")
    void test_getActiveNews() {
        Promotion promotion1 = getPromotion(1L, true);
        promotion1.setStatus(Status.ON);
        Promotion promotion2 = getPromotion(2L, true);
        promotion2.setStatus(Status.ON);
        Promotion promotion3 = getPromotion(3L, true);
        promotion3.setStatus(Status.ON);
        Promotion promotion4 = getPromotion(4L, true);
        promotion4.setStatus(Status.OFF);
        Promotion promotion5 = getPromotion(5L, true);
        promotion5.setStatus(Status.OFF);

        when(promotionRepository.findAll())
                .thenReturn(List.of(promotion1, promotion2, promotion3, promotion4, promotion5));

        List<PromotionDto> result = promotionService.getActivePromotions();

        assertEquals(3, result.size());
        assertEquals(promotion1.getId(), result.get(0).id());
        verify(promotionRepository).findAll();
    }

    private Promotion getPromotion(long id, boolean withDate) {
        Promotion promotion = new Promotion();
        promotion.setId(id);
        promotion.setDate(withDate ? LocalDate.now() : null);
        return promotion;
    }

    private PromotionDto getPromotionDto(long id, boolean withDate) {
        Promotion promotion = getPromotion(id, withDate);
        return PromotionMapper.INSTANCE.fromEntityToDto(promotion);
    }

}
