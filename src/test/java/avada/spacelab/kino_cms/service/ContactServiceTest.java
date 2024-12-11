package avada.spacelab.kino_cms.service;

import avada.spacelab.kino_cms.model.dto.admin.ContactDto;
import avada.spacelab.kino_cms.model.dto.user.ContactResponceDto;
import avada.spacelab.kino_cms.model.entity.Contact;
import avada.spacelab.kino_cms.model.entity.MainPageInfo;
import avada.spacelab.kino_cms.model.entity.Theater;
import avada.spacelab.kino_cms.repository.ContactRepository;
import avada.spacelab.kino_cms.repository.MainPageInfoRepository;
import avada.spacelab.kino_cms.repository.TheaterRepository;
import avada.spacelab.kino_cms.service.admin.impl.ContactServiceImpl;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ContactServiceTest {

    @Mock private ContactRepository contactRepository;
    @Mock private TheaterRepository theaterRepository;
    @Mock private MainPageInfoRepository mainPageInfoRepository;
    @InjectMocks private ContactServiceImpl contactService;


    @Test
    @DisplayName("Returns list of ContactDto")
    void testGetAll() {
        List<Contact> dtos = new ArrayList<>();
        for (long i = 1; i < 4; ++i) {
            dtos.add(getContact(i));
        }
        when(contactRepository.findAll()).thenReturn(dtos);
        List<ContactDto> dtosDto = contactService.getAll();
        assertEquals(3, dtosDto.size());
        assertEquals(dtos.get(0).getId(), dtosDto.get(0).id());
    }

    @Test
    @DisplayName("Returns empty list of ContactDto")
    void testGetAllWithEmptyDtoList() {
        when(contactRepository.findAll()).thenReturn(Collections.emptyList());
        List<ContactDto> dtosDto = contactService.getAll();
        assertEquals(0, dtosDto.size());
    }

    @Test
    @DisplayName("Updates existing contact")
    void testSaveWithNewContact() {
        ContactDto contactDto = getContactDto(1L);
        contactService.save(contactDto);
        verify(contactRepository).save(any(Contact.class));
    }

    @Test
    @DisplayName("Saves new contact")
    void testSaveWithExistingContact() {
        ContactDto contactDto = getContactDto(null);
        contactService.save(contactDto);
        verify(contactRepository).save(any(Contact.class));
    }

    @Test
    @DisplayName("Saves null contact")
    void testSaveWithNull() {
        when(contactRepository.save(null)).thenThrow(new RuntimeException());
        assertThrows(RuntimeException.class, () -> contactService.save(null));
        verify(contactRepository,times(1)).save(null);
    }

    @Test
    @DisplayName("Saves list of contacts simultaneously")
    void testSaveListWithNewContacts() {
        List<ContactDto> dtos = new ArrayList<>();
        for (long i = 1; i < 4; ++i) {
            dtos.add(getContactDto(null));
        }
        contactService.saveList(dtos);
        verify(contactRepository, times(3)).save(any(Contact.class));
    }

    @Test
    @DisplayName("Saves empty list of contacts")
    void testSaveListEmptyContacts() {
        contactService.saveList(Collections.emptyList());
        verify(contactRepository, times(0)).save(any(Contact.class));
    }

    @Test
    @DisplayName("Saves null list. Throws")
    void testSaveListNull() {
        assertThrows(RuntimeException.class, () -> contactService.saveList(null));
        verify(contactRepository, times(0)).save(null);
    }

    @Test
    @DisplayName("Updates list of contacts simultaneously")
    void testSaveListWithExistingContacts() {
        List<ContactDto> dtos = new ArrayList<>();
        for (long i = 1; i < 4; ++i) {
            dtos.add(getContactDto(i));
        }
        contactService.saveList(dtos);
        verify(contactRepository, times(3)).save(any(Contact.class));
    }

    @Test
    @DisplayName("test getAllWithTheater")
    void testGetAllWithTheater() {
        Contact contact1 = getContact(1L);
        Contact contact2 = getContact(2L);
        contact1.setTitle("aaa");
        contact2.setTitle("bbb");
        Theater theater = new Theater();
        theater.setTitle("aaa");

        when(contactRepository.findAll()).thenReturn(List.of(contact1, contact2));
        when(theaterRepository.findAll()).thenReturn(List.of(theater));
        when(mainPageInfoRepository.findById(1L)).thenReturn(Optional.of(new MainPageInfo()));

        List<ContactResponceDto> allWithTheater = contactService.getAllWithTheater();

        assertEquals(1, allWithTheater.size());

        verify(contactRepository, times(1)).findAll();
        verify(theaterRepository, times(1)).findAll();
        verify(mainPageInfoRepository, times(1)).findById(1L);
    }

    private ContactDto getContactDto(Long id) {
        String line = id != null ? String.valueOf(id) : "";
        String title = "title " + line;
        String address = "address " + line;
        String coordinates = "coordinates " + line;
        return new ContactDto(id, title, address, coordinates);
    }

    private Contact getContact(Long id) {
        Contact contact = new Contact();
        String line = id != null ? String.valueOf(id) : "";
        contact.setId(id);
        contact.setTitle("title" + line);
        contact.setAddress("address " + line);
        contact.setCoordinates("coordinates " + line);
        return contact;
    }

}
