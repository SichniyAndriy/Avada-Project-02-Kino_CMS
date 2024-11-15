package avada.spacelab.kino_cms.service;

import avada.spacelab.kino_cms.model.dto.admin.EditPageDto;
import avada.spacelab.kino_cms.model.dto.admin.SeoBlockDto;
import avada.spacelab.kino_cms.model.entity.EditPage;
import avada.spacelab.kino_cms.model.entity.EditPage.EditPageType;
import avada.spacelab.kino_cms.model.entity.SeoBlock;
import avada.spacelab.kino_cms.repository.EditPageRepository;
import avada.spacelab.kino_cms.service.admin.impl.EditPageServiceImpl;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EditPageServiceTest {

    @Mock
    private EditPageRepository editPageRepository;
    @InjectMocks
    private EditPageServiceImpl editPageService;

    private final Long ID = 1L;


    //=====================================\ - About - /=====================================\\

    @Test
    @DisplayName("Test getAbout() when data exist")
    void test_getAbout_whenDataExists() {
        when(editPageRepository.findByType(any()))
                .thenReturn(Optional.of(getEditPage(EditPageType.ABOUT, ID)));

        EditPageDto result = editPageService.getAbout();
        assertEquals(ID, result.id());
        verify(editPageRepository).findByType(EditPageType.ABOUT);
    }

    @Test
    @DisplayName("Test getAbout() when data doesn't exist")
    void test_getAbout_whenDataDoesNotExist() {
        when(editPageRepository.findByType(EditPageType.ABOUT))
                .thenReturn(Optional.empty());

        EditPageDto result = editPageService.getAbout();
        assertNull(result.id());
        verify(editPageRepository).findByType(EditPageType.ABOUT);
    }

    @Test
    @DisplayName("Test saveAbout() with valid parameters")
    void test_saveAbout_withValidParameters() {
        when(editPageRepository.save(any(EditPage.class)))
                .thenReturn(getEditPage(EditPageType.ABOUT, ID));

        editPageService.saveAbout(getEditPageDto(EditPageType.ABOUT, ID), "[]");

        verify(editPageRepository).save(any(EditPage.class));
    }

    @Test
    @DisplayName("Test saveAbout() with empty String parameter. Throws RuntimeException")
    void test_saveAbout_withEmptyString() {
        assertThrows(
                RuntimeException.class,
                () -> editPageService.saveAbout(getEditPageDto(EditPageType.ABOUT, ID), "")
        );
        verify(editPageRepository, never()).save(any(EditPage.class));
    }

    @Test
    @DisplayName("Test saveAbout() with null String parameter. Throws RuntimeException")
    void test_saveAbout_withNullString() {
        assertThrows(
                RuntimeException.class,
                () -> editPageService.saveAbout(getEditPageDto(EditPageType.ABOUT, ID), null)
        );
        verify(editPageRepository, never()).save(any(EditPage.class));
    }

    @Test
    @DisplayName("Test saveAbout() with invalid parameters. Throws RuntimeException")
    void test_saveAbout_withNullDtoParameter() {
        assertThrows(
                NullPointerException.class,
                () -> editPageService.saveAbout(null, "[]")
        );
        verify(editPageRepository, never()).save(any(EditPage.class));
    }

    //=====================================\ - Cafe-bar - /=====================================\\

    @Test
    @DisplayName("Test getCafeBar() when data exist")
    void test_getCafeBar_WhenDataExists() {
        when(editPageRepository.findByType(EditPageType.CAFE_BAR))
                .thenReturn(Optional.of(getEditPage(EditPageType.CAFE_BAR, ID)));

        EditPageDto result = editPageService.getCafeBar();
        assertEquals(getEditPageDto(EditPageType.CAFE_BAR, ID), result);
        verify(editPageRepository).findByType(EditPageType.CAFE_BAR);
    }

    @Test
    @DisplayName("Test getCafeBar() when data doesn't exist")
    void test_getCafeBar_whenDataDoesNotExist() {
        when(editPageRepository.findByType(EditPageType.CAFE_BAR)).thenReturn(Optional.empty());

        EditPageDto result = editPageService.getCafeBar();
        assertEquals(EditPageDto.EMPTY(), result);
        verify(editPageRepository).findByType(EditPageType.CAFE_BAR);
    }

    @Test
    @DisplayName("Test saveCafeBar() with valid parameters")
    void test_saveCafeBar_withValidParameters() {
        when(editPageRepository.save(any(EditPage.class)))
                .thenReturn(getEditPage(EditPageType.CAFE_BAR, ID));

        editPageService.saveCafeBar(getEditPageDto(EditPageType.CAFE_BAR, ID), "[]");

        verify(editPageRepository).save(any(EditPage.class));
    }

    @Test
    @DisplayName("Test saveCafeBar() with empty String parameter. Throws RuntimeException")
    void test_saveCafeBar_withEmptyString() {
        assertThrows(
                RuntimeException.class,
                () -> editPageService.saveCafeBar(getEditPageDto(EditPageType.CAFE_BAR, ID), "")
        );
        verify(editPageRepository, never()).save(any(EditPage.class));
    }

    @Test
    @DisplayName("Test saveCafeBar() with null String parameter. Throws RuntimeException")
    void test_saveCafeBar_withNullString() {
        assertThrows(
                RuntimeException.class,
                () -> editPageService.saveCafeBar(getEditPageDto(EditPageType.CAFE_BAR, ID), null)
        );
        verify(editPageRepository, never()).save(any(EditPage.class));
    }

    @Test
    @DisplayName("Test saveCafeBar() with invalid parameters. Throws RuntimeException")
    void test_saveCafeBar_withNullDtoParameter() {
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
        when(editPageRepository.findByType(EditPageType.VIP_ROOM))
                .thenReturn(Optional.of(getEditPage(EditPageType.VIP_ROOM, ID)));

        EditPageDto result = editPageService.getVipRoom();
        assertEquals(ID, result.id());
        verify(editPageRepository).findByType(EditPageType.VIP_ROOM);
    }

    @Test
    @DisplayName("Test getVipRoom() when data doesn't exist")
    void test_GetVipRoom_WhenDataDoesNotExist() {
        when(editPageRepository.findByType(EditPageType.VIP_ROOM)).thenReturn(Optional.empty());

        EditPageDto result = editPageService.getVipRoom();
        assertNull(result.id());
        verify(editPageRepository).findByType(EditPageType.VIP_ROOM);
    }

    @Test
    @DisplayName("Test saveVipRoom() with valid parameters")
    void test_saveVipRoomWithValidParameters() {
        when(editPageRepository.save(any(EditPage.class))).thenReturn(getEditPage(EditPageType.VIP_ROOM, ID));

        editPageService.saveVipRoom(getEditPageDto(EditPageType.VIP_ROOM, ID), "[]");

        verify(editPageRepository).save(any(EditPage.class));
    }

    @Test
    @DisplayName("Test saveVipRoom() with empty String parameter. Throws RuntimeException")
    void test_saveVipRoomWithEmptyString() {
        assertThrows(
                RuntimeException.class,
                () -> editPageService.saveVipRoom(getEditPageDto(EditPageType.VIP_ROOM, ID), "")
        );
        verify(editPageRepository, never()).save(any(EditPage.class));
    }

    @Test
    @DisplayName("Test saveVipRoom() with null String parameter. Throws RuntimeException")
    void test_saveVipRoomWithNullString() {
        assertThrows(
                RuntimeException.class,
                () -> editPageService.saveVipRoom(getEditPageDto(EditPageType.VIP_ROOM, ID), null)
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
        when(editPageRepository.findByType(EditPageType.ADVERTISING))
                .thenReturn(Optional.of(getEditPage(EditPageType.ADVERTISING, ID)));

        EditPageDto result = editPageService.getAdvertising();
        assertEquals(getEditPageDto(EditPageType.ADVERTISING, ID), result);
        verify(editPageRepository).findByType(EditPageType.ADVERTISING);
    }

    @Test
    @DisplayName("Test getAdvertising() when data doesn't exist")
    void test_GetAdvertising_WhenDataDoesNotExist() {
        when(editPageRepository.findByType(EditPageType.ADVERTISING)).thenReturn(Optional.empty());

        EditPageDto result = editPageService.getAdvertising();
        assertEquals(EditPageDto.EMPTY(), result);
        verify(editPageRepository).findByType(EditPageType.ADVERTISING);
    }

    @Test
    @DisplayName("Test saveAdvertising() with valid parameters")
    void test_saveAdvertisingWithValidParameters() {
        when(editPageRepository.save(any(EditPage.class)))
                .thenReturn(getEditPage(EditPageType.ADVERTISING, ID));

        editPageService.saveAdvertising(getEditPageDto(EditPageType.ADVERTISING, ID), "[]");

        verify(editPageRepository).save(any(EditPage.class));
    }

    @Test
    @DisplayName("Test saveAdvertising() with empty String parameter. Throws RuntimeException")
    void test_saveAdvertisingWithEmptyString() {
        assertThrows(
                RuntimeException.class,
                () -> editPageService.saveAdvertising(getEditPageDto(EditPageType.ADVERTISING, ID), "")
        );
        verify(editPageRepository, never()).save(any(EditPage.class));
    }

    @Test
    @DisplayName("Test saveAdvertising() with null String parameter. Throws RuntimeException")
    void test_saveAdvertisingWithNullString() {
        assertThrows(
                RuntimeException.class,
                () -> editPageService.saveAdvertising(getEditPageDto(EditPageType.ADVERTISING, ID), null)
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
        when(editPageRepository.findByType(EditPageType.CHILD_ROOM))
                .thenReturn(Optional.of(getEditPage(EditPageType.CHILD_ROOM, ID)));

        EditPageDto result = editPageService.getChildRoom();
        assertEquals(ID, result.id());
        verify(editPageRepository).findByType(EditPageType.CHILD_ROOM);
    }

    @Test
    @DisplayName("Test getChildRoom() when data doesn't exist")
    void test_GetChildRoom_WhenDataDoesNotExist() {
        when(editPageRepository.findByType(EditPageType.CHILD_ROOM)).thenReturn(Optional.empty());

        EditPageDto result = editPageService.getChildRoom();
        assertNull(result.id());
        verify(editPageRepository).findByType(EditPageType.CHILD_ROOM);
    }

    @Test
    @DisplayName("Test saveChildRoom() with valid parameters")
    void test_saveChildRoomWithValidParameters() {
        when(editPageRepository.save(any(EditPage.class)))
                .thenReturn(getEditPage(EditPageType.CHILD_ROOM, ID));

        editPageService.saveChildRoom(getEditPageDto(EditPageType.CHILD_ROOM, ID), "[]");

        verify(editPageRepository).save(any(EditPage.class));
    }

    @Test
    @DisplayName("Test saveChildRoom() with empty String parameter. Throws RuntimeException")
    void test_saveChildRoomWithEmptyString() {
        assertThrows(
                RuntimeException.class,
                () -> editPageService.saveChildRoom(getEditPageDto(EditPageType.CHILD_ROOM, ID), "")
        );
        verify(editPageRepository, never()).save(any(EditPage.class));
    }

    @Test
    @DisplayName("Test saveChildRoom() with null String parameter. Throws RuntimeException")
    void test_saveChildRoomWithNullString() {
        assertThrows(
                RuntimeException.class,
                () -> editPageService.saveChildRoom(getEditPageDto(EditPageType.CHILD_ROOM, ID), null)
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
