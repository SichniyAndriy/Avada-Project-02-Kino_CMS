package avada.spacelab.kino_cms.service.admin.impl;

import avada.spacelab.kino_cms.model.entity.User.Gender;
import avada.spacelab.kino_cms.repository.AuditoriumRepository;
import avada.spacelab.kino_cms.repository.MovieRepository;
import avada.spacelab.kino_cms.repository.TheaterRepository;
import avada.spacelab.kino_cms.repository.UserRepository;
import avada.spacelab.kino_cms.service.admin.AdminIndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminIndexServiceImpl implements AdminIndexService {

    private final UserRepository userRepository;
    private final TheaterRepository theaterRepository;
    private final AuditoriumRepository auditoriumRepository;
    private final MovieRepository movieRepository;


    public AdminIndexServiceImpl(
            @Autowired MovieRepository movieRepository,
            @Autowired TheaterRepository theaterRepository,
            @Autowired AuditoriumRepository auditoriumRepository,
            @Autowired UserRepository userRepository
    ) {
        this.movieRepository = movieRepository;
        this.theaterRepository = theaterRepository;
        this.auditoriumRepository = auditoriumRepository;
        this.userRepository = userRepository;
    }

    public long countMovies() {
        return movieRepository.count();
    }

    public long countTheaters() {
        return theaterRepository.count();
    }

    public long countAuditoriums() {
        return auditoriumRepository.count();
    }

    public long countUsers() {
        return userRepository.count();
    }

    public long countWomen() {
        return userRepository.countUserByGender(Gender.FEMALE);
    }

    public long countMen() {
        return userRepository.countUserByGender(Gender.MALE);
    }

}
