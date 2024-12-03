package avada.spacelab.kino_cms.service.admin;

import avada.spacelab.kino_cms.model.dto.admin.ContactDto;
import avada.spacelab.kino_cms.model.dto.user.ContactResponceDto;
import java.util.List;

public interface ContactService {

    List<ContactDto> getAll();

    List<ContactResponceDto> getAllWithTheater();

    void save(ContactDto contactDto);

    void saveList(List<ContactDto> contactDtos);

}
