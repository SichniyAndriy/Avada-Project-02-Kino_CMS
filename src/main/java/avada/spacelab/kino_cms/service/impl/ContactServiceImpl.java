package avada.spacelab.kino_cms.service.impl;

import avada.spacelab.kino_cms.model.dto.ContactDto;
import avada.spacelab.kino_cms.model.entity.Contact;
import avada.spacelab.kino_cms.model.mapper.ContactMapper;
import avada.spacelab.kino_cms.repository.ContactRepository;
import avada.spacelab.kino_cms.service.ContactService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContactServiceImpl implements ContactService {
    private final ContactRepository contactRepository;

    public ContactServiceImpl(
            @Autowired ContactRepository contactRepository
    ) {
        this.contactRepository = contactRepository;
    }

    public List<ContactDto> getAll() {
        List<Contact> contacts = contactRepository.findAll();
        return contacts.stream()
                .map(ContactMapper.INSTANCE::fromEntityToDto)
                .toList();
    }

    public void save(ContactDto contactDto) {
        Contact contact = ContactMapper.INSTANCE.fromDtoToEntity(contactDto);
        contactRepository.save(contact);
    }

    public void saveList(List<ContactDto> contactDtos) {
        contactDtos.stream()
                .map(ContactMapper.INSTANCE::fromDtoToEntity)
                .forEach(contactRepository::save);
    }
}
