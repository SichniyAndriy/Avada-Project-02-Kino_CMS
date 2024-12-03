package avada.spacelab.kino_cms.service;

import avada.spacelab.kino_cms.model.dto.admin.TheaterDto;
import avada.spacelab.kino_cms.model.dto.user.TheaterResponceDto;
import avada.spacelab.kino_cms.model.entity.Auditorium;
import avada.spacelab.kino_cms.model.entity.SeoBlock;
import avada.spacelab.kino_cms.model.entity.Theater;
import avada.spacelab.kino_cms.model.entity.TheaterPicture;
import avada.spacelab.kino_cms.model.mapper.admin.TheaterMapper;
import avada.spacelab.kino_cms.repository.AuditoriumRepository;
import avada.spacelab.kino_cms.repository.ScheduleRepository;
import avada.spacelab.kino_cms.repository.TheaterRepository;
import avada.spacelab.kino_cms.service.admin.impl.TheaterServiceImpl;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.LongStream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicNode;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TheaterServiceTest {

    @Mock private TheaterRepository theaterRepository;
    @Mock private AuditoriumRepository auditoriumRepository;
    @Mock private ScheduleRepository scheduleRepository;
    @InjectMocks private TheaterServiceImpl theaterService;

    private final long ID = 1L;


    @TestFactory
    @DisplayName("Test getAllTheaters()")
    List<DynamicNode> test_getAllTheaters() {
        return List.of(
                dynamicTest(
                        "With existed list",
                        () -> {
                            final int SIZE = 3;
                            List<Theater> theaters = LongStream.rangeClosed(1, SIZE).mapToObj(i -> {
                                Theater theater = new Theater();
                                theater.setId(i);
                                return theater;
                            }).toList();

                            when(theaterRepository.findAll()).thenReturn(theaters);

                            List<TheaterDto> result = theaterService.getAllTheaters();

                            assertAll(
                                    () -> assertEquals(SIZE, result.size()),
                                    () -> assertEquals(theaters.getFirst().getId(), result.getFirst().id()),
                                    () -> assertEquals(theaters.getLast().getId(), result.getLast().id())
                            );
                            verify(theaterRepository, times(1)).findAll();

                        }
                ),
                dynamicTest(
                        "With empty list",
                        () -> {
                            when(theaterRepository.findAll()).thenReturn(Collections.emptyList());

                            List<TheaterDto> result = theaterService.getAllTheaters();

                            assertEquals(0, result.size());
                            verify(theaterRepository, times(2)).findAll();
                        }
                ),
                dynamicTest(
                        "With null list",
                        () -> {
                            when(theaterRepository.findAll()).thenReturn(null);

                            assertThrows(NullPointerException.class, () ->  theaterService.getAllTheaters());
                            verify(theaterRepository, times(3)).findAll();
                        }
                )
        );
    }

    @TestFactory
    @DisplayName("Test getById()")
    List<DynamicNode> test_getById() {
        return List.of(
                dynamicTest(
                        "With valid id and not empty SeoBlock",
                        () -> {
                            Theater theater = new Theater();
                            theater.setId(ID);
                            theater.setSeoBlock(new SeoBlock());

                            when(theaterRepository.findById(anyLong())).
                                    thenReturn(Optional.of(theater));

                            TheaterDto result = theaterService.getById(ID);

                            assertEquals(ID, result.id());
                            assertNotNull(result.seoBlock());
                            verify(theaterRepository, times(1)).findById(anyLong());
                        }
                ),
                dynamicTest(
                        "With valid id and empty SeoBlock",
                        () -> {
                            Theater theater = new Theater();
                            theater.setId(ID);

                            when(theaterRepository.findById(anyLong())).
                                    thenReturn(Optional.of(theater));

                            TheaterDto result = theaterService.getById(ID);

                            assertEquals(ID, result.id());
                            assertNotNull(result.seoBlock());
                            verify(theaterRepository, times(2)).findById(anyLong());
                        }
                ),
                dynamicTest(
                        "With invalid id",
                        () -> {
                            when(theaterRepository.findById(anyLong())).
                                    thenReturn(Optional.empty());

                            TheaterDto result = theaterService.getById(0);

                            assertNull(result.id());
                            verify(theaterRepository, times(3)).findById(anyLong());
                        }
                )
        );
    }

    @TestFactory
    @DisplayName("Test save()")
    List<DynamicNode> test_save() {
        return List.of(
                dynamicTest(
                        "With valid param and valid json and not empty SeoBlock",
                        () -> {
                            Theater theater = new Theater();
                            theater.setId(ID);
                            theater.setSeoBlock(new SeoBlock());
                            theater.setPictures(List.of(new TheaterPicture(), new TheaterPicture()));
                            theater.setAuditoriums(List.of(new Auditorium()));
                            Auditorium auditorium = new Auditorium();
                            auditorium.setId(ID);

                            when(theaterRepository.save(any(Theater.class)))
                                    .thenReturn(theater);
                            when(auditoriumRepository.findAuditoriumsByTheaterId(anyLong()))
                                    .thenReturn(List.of(auditorium));

                            TheaterDto result = theaterService.save(getTheaterDto(true), "[{},{}]");

                            assertAll(
                                    () -> assertEquals(ID, result.id()),
                                    () -> assertNotNull(result.seoBlock()),
                                    () -> assertNotNull(result.auditoriums()),
                                    () -> assertEquals(1, result.auditoriums().size()),
                                    () -> assertNotNull(result.pictures()),
                                    () -> assertEquals(2, result.pictures().size())
                            );
                            verify(theaterRepository, times(1)).save(any(Theater.class));
                            verify(auditoriumRepository, times(1)).findAuditoriumsByTheaterId(anyLong());
                        }
                ),
                dynamicTest(
                        "With valid param and valid json and empty SeoBlock",
                        () -> {
                            Theater theater = new Theater();
                            theater.setId(ID);
                            theater.setPictures(List.of(new TheaterPicture(), new TheaterPicture()));
                            theater.setAuditoriums(List.of(new Auditorium()));
                            Auditorium auditorium = new Auditorium();
                            auditorium.setId(ID);

                            when(theaterRepository.save(any(Theater.class)))
                                    .thenReturn(theater);
                            when(auditoriumRepository.findAuditoriumsByTheaterId(anyLong()))
                                    .thenReturn(List.of(auditorium));

                            TheaterDto result = theaterService.save(getTheaterDto(false), "[{},{}]");

                            assertAll(
                                    () -> assertEquals(ID, result.id()),
                                    () -> assertNull(result.seoBlock()),
                                    () -> assertNotNull(result.auditoriums()),
                                    () -> assertEquals(1, result.auditoriums().size()),
                                    () -> assertNotNull(result.pictures()),
                                    () -> assertEquals(2, result.pictures().size())
                            );
                            verify(theaterRepository, times(2)).save(any(Theater.class));
                            verify(auditoriumRepository, times(2)).findAuditoriumsByTheaterId(anyLong());
                        }
                ),
                dynamicTest(
                        "With valid param and invalid json",
                        () -> {
                            assertThrows(
                                    RuntimeException.class,
                                    () -> theaterService.save(getTheaterDto(false), "qwerty")
                            );
                            verify(theaterRepository, times(2)).save(any(Theater.class));
                            verify(auditoriumRepository, times(2)).findAuditoriumsByTheaterId(anyLong());
                        }
                ),
                dynamicTest(
                        "With null param",
                        () -> {
                            assertThrows(
                                    NullPointerException.class,
                                    () -> theaterService.save(null, null)
                            );
                            verify(theaterRepository, times(2)).save(any(Theater.class));
                            verify(auditoriumRepository, times(2)).findAuditoriumsByTheaterId(anyLong());
                        }
                )

        );
    }

    @Test
    void deleteById() {
        Auditorium auditorium = new Auditorium();
        auditorium.setId(ID);

        doReturn(List.of(auditorium)).when(auditoriumRepository)
                .findAuditoriumsByTheaterId(anyLong());
        doNothing().when(scheduleRepository)
                .deleteAllByAuditoriumId(anyLong());
        doNothing().when(theaterRepository)
                .deleteById(anyLong());

        theaterService.deleteById(ID);

        InOrder inOrder = inOrder(theaterRepository, auditoriumRepository, scheduleRepository);
        inOrder.verify(auditoriumRepository, times(1)).findAuditoriumsByTheaterId(anyLong());
        inOrder.verify(scheduleRepository, times(1)).deleteAllByAuditoriumId(anyLong());
        inOrder.verify(theaterRepository, times(1)).deleteById(anyLong());
    }

    @Test
    @DisplayName("test getAllTheaterResponceDtos")
    void getAllTheaterResponceDtos() {
        when(theaterRepository.findTheaterResponceDtos())
                .thenReturn(List.of("1;aaa;bbb","2;empty;ccc","3;empty;empty"));

        List<TheaterResponceDto> result = theaterService.getAllTheaterResponceDtos();

        assertEquals(3, result.size());
        verify(theaterRepository, times(1)).findTheaterResponceDtos();
    }

    private TheaterDto getTheaterDto(boolean isSeoBlock) {
        Theater theater = new Theater();
        theater.setId(ID);
        if (isSeoBlock) {
            theater.setSeoBlock(new SeoBlock());
        }
        return TheaterMapper.INSTANCE.fromEntityToDto(theater);
    }

}
