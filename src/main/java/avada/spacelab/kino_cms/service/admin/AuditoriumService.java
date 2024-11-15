package avada.spacelab.kino_cms.service.admin;

import avada.spacelab.kino_cms.model.dto.admin.AuditoriumDto;

public interface AuditoriumService {

    AuditoriumDto getById(long id);

    void deleteAuditoriumById(long audId);

    void save(AuditoriumDto auditoriumDto, String picturesJson);

}
