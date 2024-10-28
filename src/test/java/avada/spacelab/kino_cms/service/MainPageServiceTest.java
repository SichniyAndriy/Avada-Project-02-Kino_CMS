package avada.spacelab.kino_cms.service;

import avada.spacelab.kino_cms.model.dto.MainPageInfoDto;
import avada.spacelab.kino_cms.model.entity.MainPageBanner;
import avada.spacelab.kino_cms.model.entity.MainPageBanner.Replacement;
import avada.spacelab.kino_cms.model.entity.MainPageInfo;
import avada.spacelab.kino_cms.model.entity.SeoBlock;
import avada.spacelab.kino_cms.repository.MainPageBannersRepository;
import avada.spacelab.kino_cms.repository.MainPageInfoRepository;
import avada.spacelab.kino_cms.service.impl.MainPageServiceImpl;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.LongStream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicNode;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MainPageServiceTest {
    @Mock
    private MainPageBannersRepository mainPageBannersRepository;
    @Mock
    private MainPageInfoRepository mainPageInfoRepository;
    @InjectMocks
    private MainPageServiceImpl mainPageService;

    private final int FULL_SIZE = 5;
    private final int EMPTY_SIZE = 0;
    private final long FIRST_ID = 1;
    private final long LAST_ID = 5;


    @TestFactory
    @DisplayName("Test getAllByReplacement() when data exist")
    List<DynamicNode> test_getAllByReplacement_WhenDataExisted() {
        return List.of(
                dynamicTest(
                        "Call getAllByReplacement() for UP_BANNER",
                        () -> {
                            List<MainPageBanner> upBanners =
                                     getMainPageBanners(Replacement.UP_BANNER);

                            when(mainPageBannersRepository.findAllByPlace(Replacement.UP_BANNER))
                                    .thenReturn(upBanners);

                            final List<MainPageBanner> result = mainPageService.getAllByReplacement(Replacement.UP_BANNER);

                            assertEquals(FULL_SIZE, result.size());
                            assertEquals(FIRST_ID, result.getFirst().getId());
                            assertEquals(LAST_ID, result.getLast().getId());
                            assertEquals(Replacement.UP_BANNER, result.getFirst().getPlace());
                            assertEquals(Replacement.UP_BANNER, result.getLast().getPlace());
                            verify(mainPageBannersRepository).findAllByPlace(Replacement.UP_BANNER);
                        }
                ),
                dynamicTest(
                        "Call getAllByReplacement() for SLASH_BANNER",
                        () -> {
                            List<MainPageBanner> slashBanners =
                                    getMainPageBanners(Replacement.SLASH_BANNER);

                            when(mainPageBannersRepository.findAllByPlace(Replacement.SLASH_BANNER))
                                    .thenReturn(slashBanners);

                            final List<MainPageBanner> result = mainPageService.getAllByReplacement(Replacement.SLASH_BANNER);

                            assertEquals(FULL_SIZE, result.size());
                            assertEquals(FIRST_ID, result.getFirst().getId());
                            assertEquals(LAST_ID, result.getLast().getId());
                            assertEquals(Replacement.SLASH_BANNER, result.getFirst().getPlace());
                            assertEquals(Replacement.SLASH_BANNER, result.getLast().getPlace());
                            verify(mainPageBannersRepository).findAllByPlace(Replacement.SLASH_BANNER);
                        }
                ),
                dynamicTest(
                        "Call getAllByReplacement() for BOTTOM_PROMOTION",
                        () -> {
                            List<MainPageBanner> bottomPromotions =
                                    getMainPageBanners(Replacement.BOTTOM_PROMOTION);

                            when(mainPageBannersRepository.findAllByPlace(Replacement.BOTTOM_PROMOTION))
                                    .thenReturn(bottomPromotions);

                            final List<MainPageBanner> result = mainPageService.getAllByReplacement(Replacement.BOTTOM_PROMOTION);

                            assertEquals(FULL_SIZE, result.size());
                            assertEquals(FIRST_ID, result.getFirst().getId());
                            assertEquals(LAST_ID, result.getLast().getId());
                            assertEquals(Replacement.BOTTOM_PROMOTION, result.getFirst().getPlace());
                            assertEquals(Replacement.BOTTOM_PROMOTION, result.getLast().getPlace());
                            verify(mainPageBannersRepository).findAllByPlace(Replacement.BOTTOM_PROMOTION);
                        }
                )
        );
    }

    @TestFactory
    @DisplayName("Test getAllByReplacement() when data doesn't exist")
    List<DynamicNode> test_getAllByReplacement_WhenDataNotExisted() {
        return List.of(
                dynamicTest(
                        "Call getAllByReplacement() for UP_BANNER",
                        () -> {
                            when(mainPageBannersRepository.findAllByPlace(Replacement.UP_BANNER))
                                    .thenReturn(Collections.emptyList());

                            List<MainPageBanner> result = mainPageService.getAllByReplacement(Replacement.UP_BANNER);

                            assertAll(
                                    () -> assertEquals(0, result.size()),
                                    () -> assertThrows(NoSuchElementException.class, () -> result.getFirst()),
                                    () -> assertThrows(NoSuchElementException.class, () -> result.getLast())
                            );
                            verify(mainPageBannersRepository).findAllByPlace(Replacement.UP_BANNER);
                        }
                ),
                dynamicTest(
                        "Call getAllByReplacement() for SLASH_BANNER",
                        () -> {
                            when(mainPageBannersRepository.findAllByPlace(Replacement.SLASH_BANNER))
                                    .thenReturn(Collections.emptyList());

                            List<MainPageBanner> result = mainPageService.getAllByReplacement(Replacement.SLASH_BANNER);

                            assertAll(
                                    () -> assertEquals(0, result.size()),
                                    () -> assertThrows(NoSuchElementException.class, () -> result.getFirst()),
                                    () -> assertThrows(NoSuchElementException.class, () -> result.getLast())
                            );
                            verify(mainPageBannersRepository).findAllByPlace(Replacement.SLASH_BANNER);
                        }
                ),
                dynamicTest(
                        "Call getAllByReplacement() for BOTTOM_PROMOTION",
                        () -> {
                            when(mainPageBannersRepository.findAllByPlace(Replacement.BOTTOM_PROMOTION))
                                    .thenReturn(Collections.emptyList());

                            List<MainPageBanner> result = mainPageService.getAllByReplacement(Replacement.BOTTOM_PROMOTION);

                            assertAll(
                                    () -> assertEquals(0, result.size()),
                                    () -> assertThrows(NoSuchElementException.class, () -> result.getFirst()),
                                    () -> assertThrows(NoSuchElementException.class, () -> result.getLast())
                            );
                            verify(mainPageBannersRepository).findAllByPlace(Replacement.BOTTOM_PROMOTION);
                        }
                )
        );
    }

    @Test
    @DisplayName("\"Test getAllByReplacement() with null param")
    void test_getAllByReplacement_WithNullParam() {
        doThrow(NullPointerException.class)
                .when(mainPageBannersRepository)
                .findAllByPlace(isNull());

        assertThrows(
                NullPointerException.class,
                () -> mainPageService.getAllByReplacement(null)
        );
        verify(mainPageBannersRepository).findAllByPlace(isNull());
    }

    @ParameterizedTest
    @EnumSource(Replacement.class)
    @DisplayName("Test deleteAllByReplacement() with valid param")
    void test_deleteAllByReplacement_paramOk(Replacement type) {
        mainPageService.deleteAllByReplacement(type);

        verify(mainPageBannersRepository).removeAllByPlaceEquals(type);
    }

    @Test
    @DisplayName("Test deleteAllByReplacement() with null param")
    void test_deleteAllByReplacement_paramNotOk() {
        doThrow(NullPointerException.class)
                .when(mainPageBannersRepository)
                .removeAllByPlaceEquals(isNull());

        assertThrows(
                NullPointerException.class,
                () -> mainPageService.deleteAllByReplacement(isNull())
        );
        verify(mainPageBannersRepository).removeAllByPlaceEquals(isNull());
    }

    @ParameterizedTest
    @EnumSource(Replacement.class)
    @DisplayName("Test save() with valid param")
    void test_save_withValidParam(Replacement type) {
        MainPageBanner newBanner = getMainPageBanner(type, null);
        MainPageBanner banner = getMainPageBanner(type, FIRST_ID);

        when(mainPageBannersRepository.save(newBanner)).thenReturn(banner);

        MainPageBanner saved = mainPageService.save(newBanner);

        assertEquals(banner, saved);
        assertEquals(FIRST_ID, saved.getId());
        assertEquals(type, saved.getPlace());
        verify(mainPageBannersRepository).save(newBanner);
    }

    @Test
    @DisplayName("Test save() with null param")
    void test_save_withInvalidParam() {
        doThrow(NullPointerException.class)
                .when(mainPageBannersRepository)
                .save(isNull());

        assertThrows(
                NullPointerException.class,
                () -> mainPageService.save(isNull())
        );
        verify(mainPageBannersRepository).save(isNull());
    }

    @ParameterizedTest
    @EnumSource(Replacement.class)
    @DisplayName("Test saveAll() with valid param")
    void test_saveAll_withValidParam(Replacement type) {
        List<MainPageBanner> list = getMainPageBanners(type);

        when(mainPageBannersRepository.saveAllAndFlush(anyList())).thenReturn(list);

        List<MainPageBanner> saved = mainPageService.saveAll(anyList());

        assertAll(
                ()-> assertEquals(FULL_SIZE, saved.size()),
                () -> assertEquals(FIRST_ID, saved.getFirst().getId()),
                () -> assertEquals(LAST_ID, saved.getLast().getId()),
                () -> assertEquals(type, saved.getFirst().getPlace()),
                () -> assertEquals(type, saved.getLast().getPlace())
        );
        verify(mainPageBannersRepository).saveAllAndFlush(anyList());
    }

    @ParameterizedTest
    @EnumSource(Replacement.class)
    @DisplayName("Test saveAll() with empty list")
    void test_saveAll_withEmptyList(Replacement type) {
        List<MainPageBanner> emptyList = Collections.emptyList();

        when(mainPageBannersRepository.saveAllAndFlush(anyList())).thenReturn(emptyList);

        List<MainPageBanner> saved = mainPageService.saveAll(anyList());

        assertEquals(EMPTY_SIZE, saved.size());
        verify(mainPageBannersRepository).saveAllAndFlush(anyList());
    }

    @Test
    @DisplayName("Test saveAll() with null param")
    void test_saveAll_withNullParam() {
        when(mainPageBannersRepository.saveAllAndFlush(isNull()))
                .thenReturn(Collections.emptyList());

        List<MainPageBanner> list = mainPageService.saveAll(null);

        assertEquals(EMPTY_SIZE, list.size());
        verify(mainPageBannersRepository).saveAllAndFlush(isNull());
    }

    @Test
    @DisplayName("Test saveInfo() with valid param")
    void test_saveInfo_WithValidParam() {
        MainPageInfo mainPageInfoNullId = new MainPageInfo();
        MainPageInfo mainPageInfo = new MainPageInfo();
        mainPageInfo.setId(FIRST_ID);

        when(mainPageInfoRepository.save(mainPageInfoNullId))
                .thenReturn(mainPageInfo);

        mainPageService.saveInfo(mainPageInfoNullId);

        verify(mainPageInfoRepository).save(mainPageInfoNullId);
    }

    @Test
    @DisplayName("Test saveInfo() with null param")
    void test_saveInfo_WithNullParam() {
        when(mainPageInfoRepository.save(isNull()))
                .thenReturn(null);

        mainPageService.saveInfo(null);

        verify(mainPageInfoRepository).save(isNull());
    }

    @Test
    @DisplayName("Test saveInfoDto() with valid param")
    void test_saveInfoDto_withValidParam() {
        MainPageInfo mainPageInfo = new MainPageInfo();
        MainPageInfoDto mainPageInfoDto = new MainPageInfoDto(
                FIRST_ID,
                null,
                null,
                null,
                null
        );
        mainPageInfo.setId(FIRST_ID);

        when(mainPageInfoRepository.save(any(MainPageInfo.class)))
                .thenReturn(mainPageInfo);

        mainPageService.saveInfoDto(mainPageInfoDto);

        verify(mainPageInfoRepository).save(any(MainPageInfo.class));
    }

    @Test
    @DisplayName("Test saveInfoDto() with null param")
    void test_saveInfoDto_withNullParam() {
        when(mainPageInfoRepository.save(isNull()))
                .thenReturn(null);

        mainPageService.saveInfoDto(null);

        verify(mainPageInfoRepository).save(isNull());
    }

    @Test
    @DisplayName("Test getInfo() with valid param and SeoBlock")
    void test_getInfo_WithValidParamAndSeoBlock() {
        MainPageInfo mainPageInfo = new MainPageInfo();
        mainPageInfo.setId(FIRST_ID);
        mainPageInfo.setSeoBlock(new SeoBlock());

        when(mainPageInfoRepository.findById(FIRST_ID))
                .thenReturn(Optional.of(mainPageInfo));

        MainPageInfoDto info = mainPageService.getInfo();

        assertEquals(FIRST_ID, info.id());
        verify(mainPageInfoRepository).findById(FIRST_ID);
    }

    @Test
    @DisplayName("Test getInfo() with valid param and null SeoBlock")
    void test_getInfo_WithValidParamAndNullSeoBlock() {
        MainPageInfo mainPageInfo = new MainPageInfo();
        mainPageInfo.setId(FIRST_ID);

        when(mainPageInfoRepository.findById(FIRST_ID))
                .thenReturn(Optional.of(mainPageInfo));

        MainPageInfoDto info = mainPageService.getInfo();

        assertEquals(FIRST_ID, info.id());
        verify(mainPageInfoRepository).findById(FIRST_ID);
    }

    @Test
    @DisplayName("Test getInfo() with invalid param")
    void test_getInfo_WithInvalidParamAndNullSeoBlock() {
        MainPageInfo mainPageInfo = new MainPageInfo();
        mainPageInfo.setId(FIRST_ID);

        when(mainPageInfoRepository.findById(FIRST_ID)).thenReturn(null);

        assertThrows(
                NullPointerException.class,
                () -> mainPageService.getInfo()
        );
        verify(mainPageInfoRepository).findById(FIRST_ID);
    }

    private List<MainPageBanner> getMainPageBanners(Replacement replacement) {
        return getMainPageBanners(replacement, false);
    }

    private List<MainPageBanner> getMainPageBanners(Replacement replacement, boolean isNullId) {
        return LongStream
                .iterate(1, i -> ++i)
                .limit(FULL_SIZE)
                .mapToObj(i -> getMainPageBanner(replacement, isNullId ? null : i))
                .toList();
    }

    private MainPageBanner getMainPageBanner(Replacement replacement, Long id) {
        MainPageBanner mainPageBanner = new MainPageBanner();
        mainPageBanner.setId(id);
        mainPageBanner.setPlace(replacement);
        return mainPageBanner;
    }

}
