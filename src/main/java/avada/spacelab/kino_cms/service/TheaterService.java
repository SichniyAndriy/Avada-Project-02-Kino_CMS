package avada.spacelab.kino_cms.service;

import avada.spacelab.kino_cms.model.dto.TheaterDto;
import java.util.List;

public interface TheaterService {

    List<TheaterDto> getAllTheaters();

    TheaterDto getById(long id);

    TheaterDto save(TheaterDto theaterDto, String picturesJson);

    void deleteById(long id);

}
