package avada.spacelab.kino_cms.service;

import avada.spacelab.kino_cms.model.dto.TheaterDto;
import avada.spacelab.kino_cms.model.mapper.TheaterMapper;
import avada.spacelab.kino_cms.repository.TheaterRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
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

    public List<TheaterDto> getAllTheaters() {
        return theaterRepository.findAll().stream()
                .map(TheaterMapper.INSTANCE::fromEntityToDto)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public TheaterDto findTheaterById(long id) {
        return TheaterMapper.INSTANCE.fromEntityToDto(theaterRepository.findTheaterById(id));
    }
}
