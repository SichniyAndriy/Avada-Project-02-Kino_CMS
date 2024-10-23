package avada.spacelab.kino_cms.service;

import avada.spacelab.kino_cms.model.dto.AuditoriumDto;
import avada.spacelab.kino_cms.model.dto.SeoBlockDto;
import avada.spacelab.kino_cms.model.entity.Auditorium;
import avada.spacelab.kino_cms.model.entity.Theater;
import avada.spacelab.kino_cms.repository.AuditoriumRepository;
import avada.spacelab.kino_cms.repository.TheaterRepository;
import java.time.LocalDate;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class AuditoriumServiceTest {
    @Autowired private AuditoriumService auditoriumService;
    @MockBean private AuditoriumRepository auditoriumRepository;
    @MockBean private TheaterRepository theaterRepository;

    final long ID = 1;

    @BeforeEach
    void setUp() {
        assertNotNull(auditoriumService);
        assertNotNull(auditoriumRepository);
    }

    @Test
    @DisplayName("Should return correct auditorium when getById is called with a valid ID")
    void testGetByIdReturnsCorrectAuditorium() {
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
    @DisplayName("Should return null when getById is called with an invalid ID")
    void testGetByIdReturnsNullForInvalidId() {
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
    @DisplayName("Should call deleteAuditoriumById on the repository")
    void testDeleteAuditoriumByIdCallsRepository() {
        for (long i = 0; i < 4; ++i) {
            auditoriumService.deleteAuditoriumById(i);
            verify(auditoriumRepository).deleteById(i);
        }
    }

    @Test
    @DisplayName("Should save auditorium when save is called with valid parameters")
    void testSaveCallsRepositorySave() {
        AuditoriumDto auditoriumDto = getAuditoriumDto();
        Theater theater = new Theater();
        theater.setId(ID);
        theater.setTitle("Theater$Mock");

        when(theaterRepository.findTheaterByIdAuditorium(ID)).thenReturn(theater);
        auditoriumService.save(auditoriumDto, "[]");
        verify(auditoriumRepository, times(1)).save(any(Auditorium.class));
    }

    @Test
    @DisplayName("Should throw exception when save is called with null parameters")
    void testSaveThrowsExceptionForNullParameters() {
        AuditoriumDto auditoriumDto = getAuditoriumDto();
        Theater theater = new Theater();
        theater.setId(ID);
        theater.setTitle("Theater$Mock");

        when(theaterRepository.findTheaterByIdAuditorium(ID)).thenReturn(theater);
        assertThrows(RuntimeException.class, () -> auditoriumService.save(auditoriumDto, null));
        verify(auditoriumRepository, times(0)).save(any(Auditorium.class));
    }

    @Test
    @DisplayName("\"Calling save() with picturesJson \"\" empty string")
    void test_SaveWithEmptyString_Throws_NotOk() {

        AuditoriumDto auditoriumDto = getAuditoriumDto();
        Theater theater = new Theater();
        theater.setId(ID);
        theater.setTitle("Theater$Mock");

        when(theaterRepository.findTheaterByIdAuditorium(ID)).thenReturn(theater);
        assertThrows(RuntimeException.class, () -> auditoriumService.save(auditoriumDto, ""));
        verify(auditoriumRepository, times(0)).save(any(Auditorium.class));
    }

    private AuditoriumDto getAuditoriumDto() {
        return new AuditoriumDto(
                ID,
                (int) ID,
                "description",
                LocalDate.now(),
                "https://scheme.org",
                "https://banner.org",
                null,
                SeoBlockDto.EMPTY()
        );
    }
}
