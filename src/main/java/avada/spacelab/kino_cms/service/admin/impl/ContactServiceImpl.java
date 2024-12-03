package avada.spacelab.kino_cms.service.admin.impl;

import avada.spacelab.kino_cms.model.dto.admin.ContactDto;
import avada.spacelab.kino_cms.model.dto.user.ContactResponceDto;
import avada.spacelab.kino_cms.model.entity.Contact;
import avada.spacelab.kino_cms.model.entity.MainPageInfo;
import avada.spacelab.kino_cms.model.entity.Theater;
import avada.spacelab.kino_cms.model.mapper.admin.ContactMapper;
import avada.spacelab.kino_cms.repository.ContactRepository;
import avada.spacelab.kino_cms.repository.MainPageInfoRepository;
import avada.spacelab.kino_cms.repository.TheaterRepository;
import avada.spacelab.kino_cms.service.admin.ContactService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContactServiceImpl implements ContactService {

    private final ContactRepository contactRepository;
    private final TheaterRepository theaterRepository;
    private final MainPageInfoRepository mainPageInfoRepository;


    public ContactServiceImpl(
            @Autowired ContactRepository contactRepository,
            @Autowired TheaterRepository theaterRepository,
            @Autowired MainPageInfoRepository mainPageInfoRepository
    ) {
        this.contactRepository = contactRepository;
        this.theaterRepository = theaterRepository;
        this.mainPageInfoRepository = mainPageInfoRepository;
    }

    public List<ContactDto> getAll() {
        List<Contact> contacts = contactRepository.findAll();
        return contacts.stream()
                .map(ContactMapper.INSTANCE::fromEntityToDto)
                .toList();
    }

    public List<ContactResponceDto> getAllWithTheater() {
        List<Contact> contacts = contactRepository.findAll();
        List<Theater> theaters = theaterRepository.findAll();
        MainPageInfo info = mainPageInfoRepository.findById(1L).get();

        List<ContactResponceDto> contactResponceDtos = new ArrayList<>();
        for (Contact contact : contacts) {
            for (Theater theater : theaters) {
                if (theater.getTitle().equals(contact.getTitle())) {
                    ContactResponceDto contactResponceDto = new ContactResponceDto(
                            contact.getTitle(),
                            contact.getAddress(),
                            contact.getCoordinates(),
                            theater.getLogoUrl(),
                            theater.getBannerUrl(),
                            info.getPhoneNumber1(),
                            info.getPhoneNumber2()
                    );
                    contactResponceDtos.add(contactResponceDto);
                }
            }
        }
        return contactResponceDtos;
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
