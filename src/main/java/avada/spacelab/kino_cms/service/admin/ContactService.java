package avada.spacelab.kino_cms.service.admin;

import avada.spacelab.kino_cms.model.dto.admin.ContactDto;
import java.util.List;

public interface ContactService {

    List<ContactDto> getAll();

    void save(ContactDto contactDto);

    void saveList(List<ContactDto> contactDtos);

}
