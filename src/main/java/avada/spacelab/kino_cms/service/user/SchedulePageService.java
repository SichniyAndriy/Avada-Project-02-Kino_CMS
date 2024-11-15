package avada.spacelab.kino_cms.service.user;

import avada.spacelab.kino_cms.model.dto.user.SchedulePageResponceDto;
import avada.spacelab.kino_cms.model.entity.Schedule;
import avada.spacelab.kino_cms.repository.AuditoriumRepository;
import avada.spacelab.kino_cms.repository.MovieRepository;
import avada.spacelab.kino_cms.repository.ScheduleRepository;
import avada.spacelab.kino_cms.repository.TheaterRepository;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.stream.Collectors;
import net.datafaker.Faker;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SchedulePageService {
    private final TheaterRepository theaterRepository;
    private final ScheduleRepository scheduleRepository;
    private final MovieRepository movieRepository;
    private final AuditoriumRepository auditoriumRepository;

    public SchedulePageService(
            @Autowired TheaterRepository theaterRepository,
            @Autowired ScheduleRepository scheduleRepository,
            @Autowired MovieRepository movieRepository,
            @Autowired AuditoriumRepository auditoriumRepository
    ) {
        this.theaterRepository = theaterRepository;
        this.scheduleRepository = scheduleRepository;
        this.movieRepository = movieRepository;
        this.auditoriumRepository = auditoriumRepository;
    }

    public @NotNull ConcurrentMap<LocalDate, List<SchedulePageResponceDto>> getTableEntries() {
        List<Schedule> schedules = scheduleRepository.findAll();
        Faker faker = new Faker();
        LocalDate today = LocalDate.now();
        ConcurrentMap<Long, String> theatersCache = new ConcurrentHashMap<>();
        ConcurrentNavigableMap<LocalDate, List<SchedulePageResponceDto>> result =
                new ConcurrentSkipListMap<>();

        ConcurrentMap<LocalDate, List<SchedulePageResponceDto>> collected = schedules.parallelStream()
                .filter(schedule -> {
                    LocalDate date = schedule.getKey().getDate();
                    return date.equals(today) || date.isAfter(today);
                }).collect(Collectors.groupingByConcurrent(
                        schedule -> schedule.getKey().getDate(),
                        Collectors.mapping(schedule -> {
                            LocalDate date = schedule.getKey().getDate();
                            LocalTime time = schedule.getKey().getTime();
                            Long auditoriumId = schedule.getKey().getAuditorium().getId();
                            String theaterName = theatersCache.computeIfAbsent(
                                    auditoriumId,
                                    id -> theaterRepository.findTheaterByIdAuditorium(id).getTitle()
                            );
                            String movie = schedule.getKey().getMovie().getUaTitle();
                            Integer auditoriumNumber = schedule.getKey().getAuditorium().getNumber();
                            BigDecimal price = BigDecimal.valueOf(faker.random().nextInt(1, 10) * 5L + 50)
                                    .setScale(2, RoundingMode.FLOOR);
                            return new SchedulePageResponceDto(date, time, movie, theaterName, auditoriumNumber, price);
                        }, Collectors.toList())
                ));

        collected.forEach((date, list) -> {
           list.sort(Comparator.comparing(SchedulePageResponceDto::time)
                           .thenComparing(SchedulePageResponceDto::theater)
                           .thenComparing(SchedulePageResponceDto::auditorium)
           );
           result.put(date, list);
        });

        return result;
    }

    public List<String> movies() {
        return movieRepository.findAllTitles();
    }

    public List<String> theaters() {
        return theaterRepository.findAllTitles();
    }

    public List<String> auditoriums() {
        return auditoriumRepository.findAuditoriumTitles();
    }

}
