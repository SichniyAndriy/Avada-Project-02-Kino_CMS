package avada.spacelab.kino_cms.service;

import avada.spacelab.kino_cms.repository.TheaterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TheaterService {
    private TheaterRepository theaterRepository;

    public TheaterService(
            @Autowired TheaterRepository theaterRepository
    ) {
        this.theaterRepository = theaterRepository;
    }
}
