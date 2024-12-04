package avada.spacelab.kino_cms.service.admin;

import avada.spacelab.kino_cms.model.dto.admin.AuditoriumDto;
import java.util.List;

public interface AuditoriumService {

    AuditoriumDto getById(long id);

    void deleteAuditoriumById(long audId) throws IllegalArgumentException;

    void save(AuditoriumDto auditoriumDto, String picturesJson);

    AuditoriumDto getByTheaterAndNumber(String theater, int number);

    List<AuditoriumDto> getAuditoriumsByTheaterId(long id);

}
