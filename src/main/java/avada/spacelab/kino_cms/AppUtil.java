package avada.spacelab.kino_cms;

import avada.spacelab.kino_cms.model.entity.Address;
import avada.spacelab.kino_cms.model.entity.Auditorium;
import avada.spacelab.kino_cms.model.entity.MainPageInfo;
import avada.spacelab.kino_cms.model.entity.Movie;
import avada.spacelab.kino_cms.model.entity.MovieDetails;
import avada.spacelab.kino_cms.model.entity.News;
import avada.spacelab.kino_cms.model.entity.Promotion;
import avada.spacelab.kino_cms.model.entity.Schedule;
import avada.spacelab.kino_cms.model.entity.Schedule.ScheduleCompositeKey;
import avada.spacelab.kino_cms.model.entity.Status;
import avada.spacelab.kino_cms.model.entity.Theater;
import avada.spacelab.kino_cms.model.entity.User;
import avada.spacelab.kino_cms.model.entity.User.Gender;
import avada.spacelab.kino_cms.model.entity.User.Language;
import avada.spacelab.kino_cms.repository.AuditoriumRepository;
import avada.spacelab.kino_cms.repository.MovieRepository;
import avada.spacelab.kino_cms.repository.NewsRepository;
import avada.spacelab.kino_cms.repository.PromotionRepository;
import avada.spacelab.kino_cms.repository.TheaterRepository;
import avada.spacelab.kino_cms.repository.UserRepository;
import avada.spacelab.kino_cms.service.MainPageService;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class AppUtil implements CommandLineRunner {
    private final Faker faker = new Faker();
    private final Faker localFaker = new Faker(Locale.getDefault());

    private final TheaterRepository theaterRepository;
    private final AuditoriumRepository auditoriumRepository;
    private final MovieRepository movieRepository;
    private final NewsRepository newsRepository;
    private final PromotionRepository promotionRepository;
    private final UserRepository userRepository;
    private final MainPageService mainPageService;

    public AppUtil(
            @Autowired TheaterRepository theaterRepository,
            @Autowired AuditoriumRepository auditoriumRepository,
            @Autowired MovieRepository movieRepository,
            @Autowired NewsRepository newsRepository,
            @Autowired PromotionRepository promotionRepository,
            @Autowired UserRepository userRepository,
            @Autowired MainPageService mainPageService
    ) {
        this.theaterRepository = theaterRepository;
        this.auditoriumRepository = auditoriumRepository;
        this.movieRepository = movieRepository;
        this.newsRepository = newsRepository;
        this.promotionRepository = promotionRepository;
        this.userRepository = userRepository;
        this.mainPageService = mainPageService;
    }
    
    @Override
    public void run(String... args) throws Exception {
        int n = 5;
        initTheatres(n);
        initMovies(n * 5);
        initNews(n * 3);
        initPromotions(n * 3);
        initUsers(n * 15);
        initSchedule(n, 20);
        initMainPageInfo();
    }

    private void initTheatres(int n) {
        List<Theater> theaters = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            Theater theater = new Theater();
            theater.setTitle(faker.book().title());
            theater.setDescription(faker.lorem().paragraph(faker.random().nextInt(3, 5)));
            theater.setConditions(faker.lorem().paragraph(faker.random().nextInt(4, 8)));
            int amount = faker.number().numberBetween(2, 8);
            List<Auditorium> auditoriums = new ArrayList<>();
            for (int j = 0; j < amount; ++j) {
                Auditorium auditorium = new Auditorium();
                auditorium.setDescription(
                        faker.lorem().paragraph(faker.random().nextInt(3, 5))
                );
                auditorium.setNumber(j + 1);
                auditorium.setDate(faker.timeAndDate()
                        .past(2000, TimeUnit.DAYS)
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate()
                );
                auditorium.setTheater(theater);
                auditoriums.add(auditorium);
            }
            theater.setAuditoriums(auditoriums);
            theaters.add(theater);
        }
        theaterRepository.saveAllAndFlush(theaters);
    }

    private void initMovies(int n) {
        List<Movie> movies = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            Movie movie = new Movie();
            movie.setUaTitle(faker.oscarMovie().movieName());
            movie.setNativeTitle(localFaker.oscarMovie().movieName());
            movie.setDescription(localFaker.lorem().paragraph(localFaker.random().nextInt(4, 8)));
            movie.setHas2D(localFaker.random().nextBoolean());
            movie.setHas3D(localFaker.random().nextBoolean());
            movie.setHasImax(localFaker.random().nextBoolean());
            movie.setTrailerUrl(faker.internet().url());
            movie.setAgeCenz(localFaker.random().nextInt(12, 18));
            MovieDetails movieDetails = new MovieDetails();
            movieDetails.setYear(faker.random().nextInt(2020, LocalDate.now().getYear()).toString());
            movieDetails.setCountry(localFaker.country().name());
            movieDetails.setActors(faker.name().firstName() + " " + faker.name().lastName());
            movieDetails.setDirectors(faker.name().firstName() + " " + faker.name().lastName());
            movieDetails.setScreenwriters(faker.name().firstName() + " " + faker.name().lastName());
            movieDetails.setCompositors(faker.name().firstName() + " " + faker.name().lastName());
            movieDetails.setProducers(faker.name().firstName() + " " + faker.name().lastName());
            movieDetails.setGenres(faker.book().genre());
            movieDetails.setBudget("$ " + faker.random().nextInt(1_000_000, 250_000_000));
            movieDetails.setTime(faker.time().past(7200, ChronoUnit.SECONDS, "HH:mm:ss"));
            movie.setDetails(movieDetails);
            movies.add(movie);
        }
        movieRepository.saveAllAndFlush(movies);
    }

    private void initSchedule(int n, int m) {
        List<Schedule> schedules = new ArrayList<>();
        long len = movieRepository.countById();
        for (int i = 0; i < n; ++i) {
            Optional<Movie> optionalMovie = movieRepository.findById(faker.random().nextLong(1, len));
            for (int j = 0; j < m; ++j) {
                Schedule schedule = new Schedule();
                LocalDate lowestDate = LocalDate.now().minusDays(faker.random().nextInt(5));
                LocalDate upperDate = LocalDate.now().plusDays(faker.random().nextInt(5));

                schedule.setKey(new ScheduleCompositeKey());
            }
        }
    }

    private void initNews(int n) {
        List<News> news = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            News oneNews = new News();
            oneNews.setTitle(faker.book().title());
            oneNews.setContent(localFaker.lorem().paragraph());
            oneNews.setDate(faker.timeAndDate()
                    .past(2000, TimeUnit.DAYS)
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate());
            oneNews.setTrailerUrl(faker.internet().url());
            oneNews.setStatus(faker.random().nextBoolean() ? Status.ON : Status.OFF);
            news.add(oneNews);
        }
        newsRepository.saveAllAndFlush(news);
    }

    private void initPromotions(int n) {
        List<Promotion> promotions = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            Promotion promotion = new Promotion();
            promotion.setTitle(faker.book().title());
            promotion.setContent(localFaker.lorem().paragraph());
            promotion.setDate(faker.timeAndDate()
                    .past(2000, TimeUnit.DAYS)
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate());
            promotion.setTrailerUrl(faker.internet().url());
            promotion.setStatus(faker.random().nextBoolean() ? Status.ON : Status.OFF);
            promotions.add(promotion);
        }
        promotionRepository.saveAllAndFlush(promotions);
    }

    private void initUsers(int n) {
        List<User> users = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            User user = new User();
            user.setFirstName(localFaker.name().firstName());
            user.setLastName(localFaker.name().lastName());
            user.setNickName(faker.internet().username());
            user.setPhone(faker.phoneNumber().phoneNumber());
            user.setEmail(faker.internet().emailAddress());
            user.setCardNumber(faker.finance().creditCard());
            user.setLanguage(Language.UKRAINIAN);
            user.setGender(faker.random().nextBoolean() ? Gender.MALE : Gender.FEMALE);
            user.setRegistrationDate(localFaker.timeAndDate()
                    .past(1800, TimeUnit.DAYS)
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate());
            user.setBirthDate(localFaker.timeAndDate().birthday(15, 65));

            Address address = new Address();
            address.setCity(localFaker.address().city());
            address.setZipCode(localFaker.address().zipCode());
            address.setStreet(localFaker.address().streetName());
            address.setHouseNumber(faker.address().buildingNumber());
            address.setFlatNumber(
                    faker.random().nextBoolean() ? faker.random().nextInt(1, 100).toString() : null
            );
            user.setAddress(address);
            users.add(user);
        }
        userRepository.saveAllAndFlush(users);
    }

    private void initMainPageInfo() {
        MainPageInfo mainPageInfo = new MainPageInfo();
        mainPageInfo.setPhoneNumber1(faker.phoneNumber().phoneNumber());
        mainPageInfo.setPhoneNumber2(faker.phoneNumber().phoneNumber());
        mainPageInfo.setSeoText(faker.lorem().paragraph());
        mainPageService.saveInfo(mainPageInfo);
    }
}
