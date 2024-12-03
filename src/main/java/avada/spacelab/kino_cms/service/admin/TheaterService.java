package avada.spacelab.kino_cms.service.admin;

import avada.spacelab.kino_cms.model.dto.admin.TheaterDto;
import avada.spacelab.kino_cms.model.dto.user.TheaterResponceDto;
import java.util.List;

public interface TheaterService {

    List<TheaterDto> getAllTheaters();

    List<TheaterResponceDto> getAllTheaterResponceDtos();

    TheaterDto getById(long id);

    TheaterDto save(TheaterDto theaterDto, String picturesJson);

    void deleteById(long id);

}
