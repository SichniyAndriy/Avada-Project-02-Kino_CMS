package avada.spacelab.kino_cms.service.user.impl;

import avada.spacelab.kino_cms.model.dto.user.SchedulePageResponceDto;
import avada.spacelab.kino_cms.model.entity.Schedule;
import avada.spacelab.kino_cms.repository.AuditoriumRepository;
import avada.spacelab.kino_cms.repository.MovieRepository;
import avada.spacelab.kino_cms.repository.ScheduleRepository;
import avada.spacelab.kino_cms.repository.TheaterRepository;
import avada.spacelab.kino_cms.service.user.SchedulePageService;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
public class SchedulePageServiceImpl implements SchedulePageService {
    private final TheaterRepository theaterRepository;
    private final ScheduleRepository scheduleRepository;
    private final MovieRepository movieRepository;
    private final AuditoriumRepository auditoriumRepository;

    public SchedulePageServiceImpl(
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

    @Override
    public @NotNull ConcurrentNavigableMap<LocalDate, List<SchedulePageResponceDto>> getTableEntries() {
        List<Schedule> schedules = scheduleRepository.findAll();
        Faker faker = new Faker();
        LocalDate today = LocalDate.now();
        ConcurrentMap<Long, String> theatersCache =
                new ConcurrentHashMap<>();
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
                            Long movieId = schedule.getKey().getMovie().getId();
                            String movie = schedule.getKey().getMovie().getUaTitle();
                            Integer auditoriumNumber = schedule.getKey().getAuditorium().getNumber();
                            BigDecimal price = BigDecimal.valueOf(faker.random().nextInt(1, 10) * 5L + 50)
                                    .setScale(2, RoundingMode.FLOOR);
                            return new SchedulePageResponceDto(
                                    date, time, movieId, movie, theaterName, auditoriumNumber, price
                            );
                        }, Collectors.toCollection(ArrayList::new))
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

    @Override
    public List<String> movies() {
        return movieRepository.findAllTitles();
    }

    @Override
    public List<String> theaters() {
        return theaterRepository.findAllTitles();
    }

    @Override
    public List<String> auditoriums() {
        return auditoriumRepository.findAuditoriumTitles();
    }

    @Override
    public List<String> dates() {
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        List<String> dates = new ArrayList<>();
        for (int i = 0; i < 14; ++i) {
            LocalDate date = today.plusDays(i);
            String dateString = formatter.format(date);
            dates.add(dateString);
        }
        return dates;
    }

    @Override
    public ConcurrentMap<LocalDate, List<SchedulePageResponceDto>> getChoiceResponce(
            boolean is2D,
            boolean is3D,
            boolean isImax,
            String theater,
            String dateString,
            String movie,
            String auditorium
    ) {
        ConcurrentMap<LocalDate, List<SchedulePageResponceDto>> entries = getTableEntries();
        ConcurrentMap<LocalDate, List<SchedulePageResponceDto>> result =
                doChoice(entries, is2D, is3D, isImax, theater, dateString, movie, auditorium);
        return result;
    }

    private ConcurrentMap<LocalDate, List<SchedulePageResponceDto>> doChoice(
            ConcurrentMap<LocalDate, List<SchedulePageResponceDto>> entries,
            boolean is2D,
            boolean is3D,
            boolean isImax,
            String theaterString,
            String dateString,
            String movieString,
            String auditoriumString
    ) {
        if (!dateString.equals("Дата")) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate date = LocalDate.parse(dateString, formatter);
            filterOnDate(entries, date);
        }
        if (!is2D || !is3D || !isImax) {
            filterOnType(entries, is2D, is3D, isImax);
        }
        if (!theaterString.equals("Кінотеатр")) {
            filterOnTheater(entries, theaterString);
        }
        if (!movieString.equals("Фільм")) {
            filterOnMovie(entries, movieString);
        }
        if (!auditoriumString.equals("Зал")) {
            filterOnAuditorium(entries, auditoriumString);
        }

        return entries;
    }

    private void filterOnDate(
            ConcurrentMap<LocalDate, List<SchedulePageResponceDto>> entries,
            LocalDate date
    ) {
        for (var entry : entries.entrySet()) {
            var key = entry.getKey();
            if (!key.isEqual(date)) {
                entries.remove(key);
            }
        }
    }

    private void filterOnType(
            ConcurrentMap<LocalDate, List<SchedulePageResponceDto>> entries,
            boolean is2D,
            boolean is3D,
            boolean isImax
    ) {
        for(var entry : entries.entrySet()) {
            entry.getValue().removeIf(dto -> {
                String line = movieRepository.getMovieTypes(dto.movieId(), dto.movie());
                String[] strings = line.split(";");
                boolean has2D = Boolean.parseBoolean(strings[0]);
                boolean has3D = Boolean.parseBoolean(strings[1]);
                boolean hasImax = Boolean.parseBoolean(strings[2]);
                return !(is2D && has2D || is3D && has3D || isImax && hasImax);
            });
        }
    }

    private void filterOnTheater(
            ConcurrentMap<LocalDate, List<SchedulePageResponceDto>> entries,
            String theaterString
    ) {
        for (var entry : entries.entrySet()) {
            entry.getValue().removeIf(
                    dto -> !dto.theater().equals(theaterString)
            );
        }
    }

     private void filterOnMovie(
             ConcurrentMap<LocalDate, List<SchedulePageResponceDto>> entries,
             String movieString
     ) {
        for (var entry : entries.entrySet()) {
            entry.getValue().removeIf(
                    dto -> !dto.movie().equals(movieString)
            );
        }
    }

    private void filterOnAuditorium(
            ConcurrentMap<LocalDate, List<SchedulePageResponceDto>> entries,
            String auditoriumString
    ) {
        for (var entry : entries.entrySet()) {
            entry.getValue().removeIf(dto -> {
                String line =  dto.theater() + " " + dto.auditorium();
                return !line.equals(auditoriumString);
            });
        }
    }

}
