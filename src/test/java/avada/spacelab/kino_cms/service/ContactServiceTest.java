package avada.spacelab.kino_cms.service;

import avada.spacelab.kino_cms.model.dto.admin.ContactDto;
import avada.spacelab.kino_cms.model.entity.Contact;
import avada.spacelab.kino_cms.repository.ContactRepository;
import avada.spacelab.kino_cms.service.admin.impl.ContactServiceImpl;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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

    @Mock
    private ContactRepository contactRepository;
    @InjectMocks
    private ContactServiceImpl contactService;


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
        assertEquals(dtos.getFirst().getId(), dtosDto.getFirst().id());
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
