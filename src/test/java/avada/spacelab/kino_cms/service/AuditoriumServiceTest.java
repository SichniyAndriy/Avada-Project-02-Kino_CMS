package avada.spacelab.kino_cms.service;

import avada.spacelab.kino_cms.model.dto.admin.AuditoriumDto;
import avada.spacelab.kino_cms.model.dto.admin.SeoBlockDto;
import avada.spacelab.kino_cms.model.entity.Auditorium;
import avada.spacelab.kino_cms.model.entity.SeoBlock;
import avada.spacelab.kino_cms.model.entity.Theater;
import avada.spacelab.kino_cms.model.mapper.admin.AuditoriumMapper;
import avada.spacelab.kino_cms.repository.AuditoriumRepository;
import avada.spacelab.kino_cms.repository.ScheduleRepository;
import avada.spacelab.kino_cms.repository.TheaterRepository;
import avada.spacelab.kino_cms.service.admin.impl.AuditoriumServiceImpl;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuditoriumServiceTest {

    @Mock private TheaterRepository theaterRepository;
    @Mock private AuditoriumRepository auditoriumRepository;
    @Mock private ScheduleRepository scheduleRepository;
    @InjectMocks private AuditoriumServiceImpl auditoriumService;

    final long ID = 1;


    @Test
    @DisplayName("Test getById() with valid Auditorium and SeoBlock")
    void test_getById_withValidAuditoriumAndSeoBlock() {
        for (long i = 1; i < 4; ++i) {
            Auditorium auditorium = new Auditorium();
            auditorium.setId(i);
            auditorium.setSeoBlock(new SeoBlock());
            when(auditoriumRepository.findById(i))
                    .thenReturn(Optional.of(auditorium));
        }
         assertAll(
                 "Method getById() didn't call its repository method findById()",
                 () -> assertEquals(1L, auditoriumService.getById(1L).id()),
                 () -> assertEquals(2L, auditoriumService.getById(2L).id()),
                 () -> assertEquals(3L, auditoriumService.getById(3L).id())
         );
    }

    @Test
    @DisplayName("test getById() with correct auditorium and null SeoBlock")
    void test_getById_ReturnsNullAuditorium() {
        for (long i = 1; i < 4; ++i) {
            Auditorium auditorium = new Auditorium();
            auditorium.setId(i);
            when(auditoriumRepository.findById(i))
                    .thenReturn(Optional.of(auditorium));
        }

         assertAll(
                 "Method getById() didn't call its repository method findById()",
                 () -> assertEquals(1L, auditoriumService.getById(1L).id()),
                 () -> assertEquals(2L, auditoriumService.getById(2L).id()),
                 () -> assertEquals(3L, auditoriumService.getById(3L).id())
         );
    }

    @Test
    @DisplayName("test deleteAuditoriumById with wrong id")
    void test_deleteAuditoriumById_withWrightId() {
        doNothing().when(scheduleRepository).deleteAllByAuditoriumId(anyLong());
        doNothing().when(auditoriumRepository).deleteAuditoriumById(anyLong());

        auditoriumService.deleteAuditoriumById(1L);

        verify(scheduleRepository).deleteAllByAuditoriumId(anyLong());
        verify(auditoriumRepository).deleteAuditoriumById(anyLong());
    }

    @Test
    @DisplayName("test deleteAuditoriumById with wrong id")
    void test_deleteAuditoriumById_withWrongId() {
        assertThrows(
                IllegalArgumentException.class,
                () -> auditoriumService.deleteAuditoriumById(-1L)
        );

        verify(auditoriumRepository, never()).deleteById(anyLong());
    }

    @Test
    @DisplayName("Should return null when getById is called with an invalid ID")
    void test_getById_ReturnsNullForInvalidId() {
        when(auditoriumRepository.findById(0L))
                .thenReturn(Optional.empty());
        assertNull(auditoriumService.getById(0L).id());
    }

    @Test
    @DisplayName("Should create a new SeoBlock if it's empty when getting auditorium by ID")
    void testGetByIdCreatesNewSeoBlockIfEmpty() {
        Auditorium auditorium = new Auditorium();
        auditorium.setId(1L);
        when(auditoriumRepository.findById(1L))
                .thenReturn(Optional.of(auditorium));
        assertNotNull(auditoriumService.getById(1L).seoBlock());
    }

    @Test
    @DisplayName("Should save auditorium when save is called with valid parameters")
    void testSaveCallsRepositorySave() {
        AuditoriumDto auditoriumDto = getAuditoriumDto();
        Theater theater = new Theater();
        theater.setId(ID);
        theater.setTitle("Theater$Mock");
        when(theaterRepository.findById(anyLong()))
                .thenReturn(Optional.of(theater));
        when(auditoriumRepository.save(any(Auditorium.class)))
                .thenReturn(AuditoriumMapper.INSTANCE.fromDtoToEntity(auditoriumDto));

        auditoriumService.save(auditoriumDto, "[]");

        verify(theaterRepository).findById(anyLong());
        verify(auditoriumRepository).save(any(Auditorium.class));
    }

    @Test
    @DisplayName("Should save auditorium when save is called with valid parameters and null date")
    void test_save_withValidParamsAndNullDate() {
        AuditoriumDto auditoriumDto = new AuditoriumDto(
                ID,
                (int) ID,
                "description",
                null,
                "https://scheme.org",
                "https://banner.org",
                ID,
                Collections.emptyList(),
                SeoBlockDto.EMPTY()
        );
        Theater theater = new Theater();
        theater.setId(ID);
        theater.setTitle("Theater$Mock");
        when(theaterRepository.findById(anyLong()))
                .thenReturn(Optional.of(theater));
        when(auditoriumRepository.save(any(Auditorium.class)))
                .thenReturn(AuditoriumMapper.INSTANCE.fromDtoToEntity(auditoriumDto));

        auditoriumService.save(auditoriumDto, "[]");

        verify(theaterRepository).findById(anyLong());
        verify(auditoriumRepository).save(any(Auditorium.class));
    }

    @Test
    @DisplayName("Should throw exception when save is called with null string")
    void testSaveThrowsExceptionForNullParameterString() {
        AuditoriumDto auditoriumDto = getAuditoriumDto();

        assertThrows(
                RuntimeException.class,
                () -> auditoriumService.save(auditoriumDto, null)
        );
        verify(auditoriumRepository, never()).save(any(Auditorium.class));
    }

    @Test
    @DisplayName("Should throw exception when save is called with null parameters")
    void testSaveThrowsExceptionForNullParameters() {
        assertThrows(
                IllegalArgumentException.class,
                () -> auditoriumService.save(null, null)
        );
        verify(auditoriumRepository, times(0)).save(any(Auditorium.class));
    }

    @Test
    @DisplayName("\"Calling save() with picturesJson \"\" empty string")
    void test_SaveWithEmptyString_Throws_NotOk() {
        assertThrows(
                RuntimeException.class,
                () -> auditoriumService.save(getAuditoriumDto(), "")
        );
        verify(auditoriumRepository, never()).save(any(Auditorium.class));
    }

    @Test
    @DisplayName("test getByTheaterAndNumber")
    void test_getByTheaterAndNumber() {
        when(auditoriumRepository.findAuditoriumByTheaterNameAndNumber("...", 1)).thenReturn(new Auditorium());

        AuditoriumDto theaterAndNumber = auditoriumService.getByTheaterAndNumber("...", 1);
        assertNotNull(theaterAndNumber);
        assertNull(theaterAndNumber.theaterId());

        verify(auditoriumRepository).findAuditoriumByTheaterNameAndNumber("...", 1);
    }

    @Test
    @DisplayName("test getAuditoriumsByTheaterId")
    void test_getAuditoriumsByTheaterId() {
        when(auditoriumRepository.findAuditoriumsByTheaterId(1L)).thenReturn(List.of(new Auditorium()));

        List<AuditoriumDto> auditoriumsByTheaterId = auditoriumService.getAuditoriumsByTheaterId(1L);
        assertNotNull(auditoriumsByTheaterId);
        assertEquals(1, auditoriumsByTheaterId.size());
        assertNull(auditoriumsByTheaterId.get(0).theaterId());

        verify(auditoriumRepository).findAuditoriumsByTheaterId(1L);
    }

    @org.jetbrains.annotations.NotNull
    @org.jetbrains.annotations.Contract(" -> new")
    private AuditoriumDto getAuditoriumDto() {
        return new AuditoriumDto(
                ID,
                (int) ID,
                "description",
                LocalDate.now(),
                "https://scheme.org",
                "https://banner.org",
                ID,
                Collections.emptyList(),
                SeoBlockDto.EMPTY()
        );
    }

}
