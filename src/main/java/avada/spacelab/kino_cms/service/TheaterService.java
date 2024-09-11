package avada.spacelab.kino_cms.service;

import avada.spacelab.kino_cms.model.dto.TheaterDto;
import avada.spacelab.kino_cms.model.mapper.TheaterMapper;
import avada.spacelab.kino_cms.repository.TheaterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TheaterService {
    private final TheaterRepository theaterRepository;

    public TheaterService(
            @Autowired TheaterRepository theaterRepository
    ) {
        this.theaterRepository = theaterRepository;
    }

    public TheaterDto findTheaterById(long id) {
        return TheaterMapper.INSTANCE.fromEntityToDto(theaterRepository.findTheaterById(id));
    }
}
