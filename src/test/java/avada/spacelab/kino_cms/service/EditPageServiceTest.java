package avada.spacelab.kino_cms.service;

import avada.spacelab.kino_cms.model.dto.EditPageDto;
import avada.spacelab.kino_cms.model.dto.SeoBlockDto;
import avada.spacelab.kino_cms.model.entity.EditPage;
import avada.spacelab.kino_cms.model.entity.EditPage.EditPageType;
import avada.spacelab.kino_cms.model.entity.SeoBlock;
import avada.spacelab.kino_cms.repository.EditPageRepository;
import avada.spacelab.kino_cms.service.impl.EditPageServiceImpl;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
class EditPageServiceTest {

    @Mock
    private EditPageRepository editPageRepository;
    @InjectMocks
    private EditPageServiceImpl editPageService;
    private AutoCloseable openedMocks;

    private final EditPageType ABOUT_TYPE = EditPageType.ABOUT;
    private final EditPageType CAFE_BAR_TYPE = EditPageType.CAFE_BAR;
    private final EditPageType VIP_ROOM_TYPE = EditPageType.VIP_ROOM;
    private final EditPageType ADVERTISING_TYPE = EditPageType.ADVERTISING;
    private final EditPageType CHILD_ROOM_TYPE = EditPageType.CHILD_ROOM;

    private final Long ID = 1l;

    private EditPageDto aboutDto;
    private EditPage aboutEntity;
    private EditPageDto cafeBarDto;
    private EditPage cafeBarEntity;
    private EditPageDto vipRoomDto;
    private EditPage vipRoomEntity;
    private EditPageDto advertisingDto;
    private EditPage advertisingEntity;
    private EditPageDto childRoomDto;
    private EditPage childRoomEntity;

    @BeforeAll
    void setUp() {
        openedMocks = MockitoAnnotations.openMocks(this);
        aboutDto = getEditPageDto(ABOUT_TYPE, ID);
        aboutEntity = getEditPage(ABOUT_TYPE, ID);
        cafeBarDto = getEditPageDto(CAFE_BAR_TYPE, ID);
        cafeBarEntity = getEditPage(CAFE_BAR_TYPE, ID);
        vipRoomDto = getEditPageDto(VIP_ROOM_TYPE, ID);
        vipRoomEntity = getEditPage(VIP_ROOM_TYPE, ID);
        advertisingDto = getEditPageDto(ADVERTISING_TYPE, ID);
        advertisingEntity = getEditPage(ADVERTISING_TYPE, ID);
        childRoomDto = getEditPageDto(CHILD_ROOM_TYPE, ID);
        childRoomEntity = getEditPage(CHILD_ROOM_TYPE, ID);
    }

    @AfterAll
    void tearDown() throws Exception {
        openedMocks.close();
    }

    //=====================================\ - About - /=====================================\\

    @Test
    @DisplayName("Test getAbout() when data exist")
    void testGetAbout_WhenDataExists() {
        when(editPageRepository.findByType(ABOUT_TYPE))
                .thenReturn(Optional.of(aboutEntity));

        EditPageDto result = editPageService.getAbout();
        assertEquals(aboutDto, result);
        verify(editPageRepository).findByType(ABOUT_TYPE);
    }

    @Test
    @DisplayName("Test getAbout() when data doesn't exist")
    void testGetAbout_WhenDataDoesNotExist() {
        when(editPageRepository.findByType(ABOUT_TYPE))
                .thenReturn(Optional.empty());

        EditPageDto result = editPageService.getAbout();
        assertEquals(EditPageDto.EMPTY(), result);
        verify(editPageRepository).findByType(ABOUT_TYPE);
    }

    @Test
    @DisplayName("Test saveAbout() with valid parameters")
    void test_saveAboutWithValidParameters() {
        when(editPageRepository.save(any(EditPage.class)))
                .thenReturn(aboutEntity);

        editPageService.saveAbout(aboutDto, "[]");

        verify(editPageRepository).save(any(EditPage.class));
    }

    @Test
    @DisplayName("Test saveAbout() with empty String parameter. Throws RuntimeException")
    void test_saveAboutWithEmptyString() {
        assertThrows(
                RuntimeException.class,
                () -> editPageService.saveAbout(aboutDto, "")
        );
        verify(editPageRepository, never()).save(any(EditPage.class));
    }

    @Test
    @DisplayName("Test saveAbout() with null String parameter. Throws RuntimeException")
    void test_saveAboutWithNullString() {
        assertThrows(
                RuntimeException.class,
                () -> editPageService.saveAbout(aboutDto, null)
        );
        verify(editPageRepository, never()).save(any(EditPage.class));
    }

    @Test
    @DisplayName("Test saveAbout() with invalid parameters. Throws RuntimeException")
    void test_saveAboutWithNullDtoParameter() {
        assertThrows(
                NullPointerException.class,
                () -> editPageService.saveAbout(null, "[]")
        );
        verify(editPageRepository, never()).save(any(EditPage.class));
    }

    //=====================================\ - Cafe-bar - /=====================================\\

    @Test
    @DisplayName("Test getCafeBar() when data exist")
    void test_GetCafeBar_WhenDataExists() {
        when(editPageRepository.findByType(CAFE_BAR_TYPE))
                .thenReturn(Optional.of(cafeBarEntity));

        EditPageDto result = editPageService.getCafeBar();
        assertEquals(cafeBarDto, result);
        verify(editPageRepository).findByType(CAFE_BAR_TYPE);
    }

    @Test
    @DisplayName("Test getCafeBar() when data doesn't exist")
    void test_GetCafeBar_WhenDataDoesNotExist() {
        when(editPageRepository.findByType(CAFE_BAR_TYPE)).thenReturn(Optional.empty());

        EditPageDto result = editPageService.getCafeBar();
        assertEquals(EditPageDto.EMPTY(), result);
        verify(editPageRepository).findByType(CAFE_BAR_TYPE);
    }

    @Test
    @DisplayName("Test saveCafeBar() with valid parameters")
    void test_saveCafeBarWithValidParameters() {
        when(editPageRepository.save(any(EditPage.class))).thenReturn(cafeBarEntity);

        editPageService.saveCafeBar(cafeBarDto, "[]");

        verify(editPageRepository).save(any(EditPage.class));
    }

    @Test
    @DisplayName("Test saveCafeBar() with empty String parameter. Throws RuntimeException")
    void test_saveCafeBarWithEmptyString() {
        assertThrows(
                RuntimeException.class,
                () -> editPageService.saveCafeBar(cafeBarDto, "")
        );
        verify(editPageRepository, never()).save(any(EditPage.class));
    }

    @Test
    @DisplayName("Test saveCafeBar() with null String parameter. Throws RuntimeException")
    void test_saveCafeBarWithNullString() {
        assertThrows(
                RuntimeException.class,
                () -> editPageService.saveCafeBar(cafeBarDto, null)
        );
        verify(editPageRepository, never()).save(any(EditPage.class));
    }

    @Test
    @DisplayName("Test saveCafeBar() with invalid parameters. Throws RuntimeException")
    void test_saveCafeBarWithNullDtoParameter() {
        assertThrows(
                NullPointerException.class,
                () -> editPageService.saveCafeBar(null, "[]")
        );
        verify(editPageRepository, never()).save(any(EditPage.class));
    }

    //=====================================\ - VipRoom - /=====================================\\

    @Test
    @DisplayName("Test getVipRoom() when data exist")
    void test_GetVipRoom_WhenDataExists() {
        when(editPageRepository.findByType(VIP_ROOM_TYPE))
                .thenReturn(Optional.of(vipRoomEntity));

        EditPageDto result = editPageService.getVipRoom();
        assertEquals(vipRoomDto, result);
        verify(editPageRepository).findByType(VIP_ROOM_TYPE);
    }

    @Test
    @DisplayName("Test getVipRoom() when data doesn't exist")
    void test_GetVipRoom_WhenDataDoesNotExist() {
        when(editPageRepository.findByType(VIP_ROOM_TYPE)).thenReturn(Optional.empty());

        EditPageDto result = editPageService.getVipRoom();
        assertEquals(EditPageDto.EMPTY(), result);
        verify(editPageRepository).findByType(VIP_ROOM_TYPE);
    }

    @Test
    @DisplayName("Test saveVipRoom() with valid parameters")
    void test_saveVipRoomWithValidParameters() {
        when(editPageRepository.save(any(EditPage.class))).thenReturn(vipRoomEntity);

        editPageService.saveVipRoom(vipRoomDto, "[]");

        verify(editPageRepository).save(any(EditPage.class));
    }

    @Test
    @DisplayName("Test saveVipRoom() with empty String parameter. Throws RuntimeException")
    void test_saveVipRoomWithEmptyString() {
        assertThrows(
                RuntimeException.class,
                () -> editPageService.saveVipRoom(vipRoomDto, "")
        );
        verify(editPageRepository, never()).save(any(EditPage.class));
    }

    @Test
    @DisplayName("Test saveVipRoom() with null String parameter. Throws RuntimeException")
    void test_saveVipRoomWithNullString() {
        assertThrows(
                RuntimeException.class,
                () -> editPageService.saveVipRoom(vipRoomDto, null)
        );
        verify(editPageRepository, never()).save(any(EditPage.class));
    }

    @Test
    @DisplayName("Test saveVipRoom() with invalid parameters. Throws RuntimeException")
    void test_saveVipRoomWithNullDtoParameter() {
        assertThrows(
                NullPointerException.class,
                () -> editPageService.saveVipRoom(null, "[]")
        );
        verify(editPageRepository, never()).save(any(EditPage.class));
    }

    //=====================================\ - Advertising - /=====================================\\

    @Test
    @DisplayName("Test getAdvertising() when data exist")
    void test_GetAdvertising_WhenDataExists() {
        when(editPageRepository.findByType(ADVERTISING_TYPE))
                .thenReturn(Optional.of(advertisingEntity));

        EditPageDto result = editPageService.getAdvertising();
        assertEquals(advertisingDto, result);
        verify(editPageRepository).findByType(ADVERTISING_TYPE);
    }

    @Test
    @DisplayName("Test getAdvertising() when data doesn't exist")
    void test_GetAdvertising_WhenDataDoesNotExist() {
        when(editPageRepository.findByType(ADVERTISING_TYPE)).thenReturn(Optional.empty());

        EditPageDto result = editPageService.getAdvertising();
        assertEquals(EditPageDto.EMPTY(), result);
        verify(editPageRepository).findByType(ADVERTISING_TYPE);
    }

    @Test
    @DisplayName("Test saveAdvertising() with valid parameters")
    void test_saveAdvertisingWithValidParameters() {
        when(editPageRepository.save(any(EditPage.class))).thenReturn(advertisingEntity);

        editPageService.saveAdvertising(advertisingDto, "[]");

        verify(editPageRepository).save(any(EditPage.class));
    }

    @Test
    @DisplayName("Test saveAdvertising() with empty String parameter. Throws RuntimeException")
    void test_saveAdvertisingWithEmptyString() {
        assertThrows(
                RuntimeException.class,
                () -> editPageService.saveAdvertising(advertisingDto, "")
        );
        verify(editPageRepository, never()).save(any(EditPage.class));
    }

    @Test
    @DisplayName("Test saveAdvertising() with null String parameter. Throws RuntimeException")
    void test_saveAdvertisingWithNullString() {
        assertThrows(
                RuntimeException.class,
                () -> editPageService.saveAdvertising(advertisingDto, null)
        );
        verify(editPageRepository, never()).save(any(EditPage.class));
    }

    @Test
    @DisplayName("Test saveAdvertising() with invalid parameters. Throws RuntimeException")
    void test_saveAdvertisingWithNullDtoParameter() {
        assertThrows(
                NullPointerException.class,
                () -> editPageService.saveAdvertising(null, "[]")
        );
        verify(editPageRepository, never()).save(any(EditPage.class));
    }

    //=====================================\ -Child room - /=====================================\\

    @Test
    @DisplayName("Test getChildRoom() when data exist")
    void test_GetChildRoom_WhenDataExists() {
        when(editPageRepository.findByType(CHILD_ROOM_TYPE))
                .thenReturn(Optional.of(childRoomEntity));

        EditPageDto result = editPageService.getChildRoom();
        assertEquals(childRoomDto, result);
        verify(editPageRepository).findByType(CHILD_ROOM_TYPE);
    }

    @Test
    @DisplayName("Test getChildRoom() when data doesn't exist")
    void test_GetChildRoom_WhenDataDoesNotExist() {
        when(editPageRepository.findByType(CHILD_ROOM_TYPE)).thenReturn(Optional.empty());

        EditPageDto result = editPageService.getChildRoom();
        assertEquals(EditPageDto.EMPTY(), result);
        verify(editPageRepository).findByType(CHILD_ROOM_TYPE);
    }

    @Test
    @DisplayName("Test saveChildRoom() with valid parameters")
    void test_saveChildRoomWithValidParameters() {
        when(editPageRepository.save(any(EditPage.class))).thenReturn(childRoomEntity);

        editPageService.saveChildRoom(childRoomDto, "[]");

        verify(editPageRepository).save(any(EditPage.class));
    }

    @Test
    @DisplayName("Test saveChildRoom() with empty String parameter. Throws RuntimeException")
    void test_saveChildRoomWithEmptyString() {
        assertThrows(
                RuntimeException.class,
                () -> editPageService.saveChildRoom(childRoomDto, "")
        );
        verify(editPageRepository, never()).save(any(EditPage.class));
    }

    @Test
    @DisplayName("Test saveChildRoom() with null String parameter. Throws RuntimeException")
    void test_saveChildRoomWithNullString() {
        assertThrows(
                RuntimeException.class,
                () -> editPageService.saveChildRoom(childRoomDto, null)
        );
        verify(editPageRepository, never()).save(any(EditPage.class));
    }

    @Test
    @DisplayName("Test saveChildRoom() with invalid parameters. Throws RuntimeException")
    void test_saveChildRoomWithNullDtoParameter() {
        assertThrows(
                NullPointerException.class,
                () -> editPageService.saveChildRoom(null, "[]")
        );
        verify(editPageRepository, never()).save(any(EditPage.class));
    }

    //==============================================================================================\\

    private EditPageDto getEditPageDto(EditPageType type, Long id) {
        return new EditPageDto(
                id,
                null,
                null,
                null,
                null,
                type,
                SeoBlockDto.EMPTY(),
                Collections.emptyList()
        );
    }

    private EditPage getEditPage(EditPageType type, Long id) {
        EditPage editPage = new EditPage();
        editPage.setId(id);
        editPage.setType(type);
        editPage.setSeoBlock(new SeoBlock());
        editPage.setPictures(new ArrayList<>());
        return editPage;
    }

}
