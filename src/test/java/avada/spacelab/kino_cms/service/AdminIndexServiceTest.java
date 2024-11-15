package avada.spacelab.kino_cms.service;

import avada.spacelab.kino_cms.model.entity.User.Gender;
import avada.spacelab.kino_cms.repository.AuditoriumRepository;
import avada.spacelab.kino_cms.repository.MovieRepository;
import avada.spacelab.kino_cms.repository.TheaterRepository;
import avada.spacelab.kino_cms.repository.UserRepository;
import avada.spacelab.kino_cms.service.admin.impl.AdminIndexServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AdminIndexServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private TheaterRepository theaterRepository;
    @Mock
    private AuditoriumRepository auditoriumRepository;
    @Mock
    private MovieRepository movieRepository;
    @InjectMocks
    private AdminIndexServiceImpl adminIndexService;

    private final long AMOUNT = 99L;

    @Test
    void countMovies() {
        when(movieRepository.count()).thenReturn(AMOUNT);

        assertEquals(AMOUNT, adminIndexService.countMovies());
        verify(movieRepository).count();
    }

    @Test
    void countTheaters() {
        when(theaterRepository.count()).thenReturn(AMOUNT);

        assertEquals(AMOUNT, adminIndexService.countTheaters());
        verify(theaterRepository).count();
    }

    @Test
    void countAuditoriums() {
        when(auditoriumRepository.count()).thenReturn(AMOUNT);

        assertEquals(AMOUNT, adminIndexService.countAuditoriums());
        verify(auditoriumRepository).count();
    }

    @Test
    void countUsers() {
        when(userRepository.count()).thenReturn(AMOUNT);

        assertEquals(AMOUNT, adminIndexService.countUsers());
        verify(userRepository).count();
    }

    @Test
    void countWomen() {
        when(userRepository.countUserByGender(Gender.FEMALE)).thenReturn(AMOUNT);

        assertEquals(AMOUNT, adminIndexService.countWomen());
        verify(userRepository).countUserByGender(Gender.FEMALE);
    }

    @Test
    void countMen() {
        when(userRepository.countUserByGender(Gender.MALE)).thenReturn(AMOUNT);

        assertEquals(AMOUNT, adminIndexService.countMen());
        verify(userRepository).countUserByGender(Gender.MALE);
    }
}