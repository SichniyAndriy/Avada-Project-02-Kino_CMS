package avada.spacelab.kino_cms.service;

import avada.spacelab.kino_cms.model.dto.ContactDto;
import avada.spacelab.kino_cms.model.entity.Contact;
import avada.spacelab.kino_cms.model.mapper.ContactMapper;
import avada.spacelab.kino_cms.repository.ContactRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContactService {
    private final ContactRepository contactRepository;

    public ContactService(
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

    public void saveAll(List<ContactDto> contactDtos) {
        List<Contact> contacts = contactDtos.stream()
                .map(ContactMapper.INSTANCE::fromDtoToEntity)
                .toList();
        contactRepository.saveAllAndFlush(contacts);
    }

    public void saveList(List<ContactDto> contactDtos) {
        contactDtos.stream()
                .map(ContactMapper.INSTANCE::fromDtoToEntity)
                .forEach(contactRepository::save);
    }
}