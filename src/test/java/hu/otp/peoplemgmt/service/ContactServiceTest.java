package hu.otp.peoplemgmt.service;

import hu.otp.peoplemgmt.domain.Contact;
import hu.otp.peoplemgmt.domain.Person;
import hu.otp.peoplemgmt.domain.dto.ContactDTO;
import hu.otp.peoplemgmt.domain.enumeration.ContactType;
import hu.otp.peoplemgmt.repository.ContactRepository;
import hu.otp.peoplemgmt.repository.PersonRepository;
import hu.otp.peoplemgmt.service.impl.ContactServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ContactServiceTest {

    @Mock
    private ContactRepository contactRepository;

    @Mock
    private PersonRepository personRepository;

    @InjectMocks
    private ContactServiceImpl contactService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private ContactDTO createContactDTO(ContactType type, String value, String personId) {
        ContactDTO contactDTO = new ContactDTO();
        contactDTO.setContactType(type);
        contactDTO.setContactValue(value);
        contactDTO.setPersonId(personId);
        return contactDTO;
    }

    @Test
    void testSaveNewContact() {
        ContactDTO contactDTO = createContactDTO(ContactType.EMAIL, "test@test.com", "jkovacs");
        Person person = new Person();
        person.setId("jkovacs");

        when(personRepository.findById("jkovacs")).thenReturn(Optional.of(person));
        when(contactRepository.save(any(Contact.class))).thenAnswer(invocation -> {
            Contact contact = invocation.getArgument(0);
            contact.setId(1L);
            return contact;
        });

        ContactDTO result = contactService.save(contactDTO);

        assertThat(result.getId()).isEqualTo(1L);
        verify(contactRepository).save(any(Contact.class));
    }

    @Test
    void testUpdateExistingContact() {
        ContactDTO contactDTO = createContactDTO(ContactType.TELEPHONE, "+36301234567", "jkovacs");
        contactDTO.setId(1L);

        Person person = new Person();
        person.setId("jkovacs");

        Contact existingContact = new Contact();
        existingContact.setId(1L);
        existingContact.setPersonContact(person);

        when(personRepository.findById("jkovacs")).thenReturn(Optional.of(person));
        when(contactRepository.findById(1L)).thenReturn(Optional.of(existingContact));
        when(contactRepository.save(any(Contact.class))).thenAnswer(invocation -> invocation.getArgument(0));

        ContactDTO result = contactService.save(contactDTO);

        assertThat(result.getContactValue()).isEqualTo("+36301234567");
        assertThat(result.getContactType()).isEqualTo(ContactType.TELEPHONE);
        verify(contactRepository).save(any(Contact.class));
    }

    @Test
    void testThrowExceptionIfPersonNotFound() {
        ContactDTO contactDTO = createContactDTO(ContactType.EMAIL, "test@test.com", "invalid");

        when(personRepository.existsById("invalid")).thenReturn(false);

        assertThrows(IllegalArgumentException.class, () -> contactService.save(contactDTO));
        verify(contactRepository, never()).save(any(Contact.class));
    }

    @Test
    void testDelete() {
        Long existingId = 1L;
        when(contactRepository.existsById(existingId)).thenReturn(true);

        contactService.delete(existingId);

        verify(contactRepository).deleteById(existingId);
    }

    @Test
    void testDeleteNotFound() {
        Long nonExistentId = 1L;
        when(contactRepository.existsById(nonExistentId)).thenReturn(false);

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> contactService.delete(nonExistentId));

        assertEquals("Contact not found with ID: " + nonExistentId, exception.getMessage());
        verify(contactRepository, never()).deleteById(anyLong());
    }

    @Test
    void testListItems() {
        Person person = new Person();
        person.setId("jkovacs");

        Contact contact = new Contact();
        contact.setId(1L);
        contact.setContactType(ContactType.EMAIL);
        contact.setPersonContact(person);

        when(contactRepository.findAll()).thenReturn(List.of(contact));

        List<ContactDTO> result = contactService.listItems();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getContactType()).isEqualTo(ContactType.EMAIL);
    }

    @Test
    void testGetOneItem() {
        Person person = new Person();
        person.setId("jkovacs");

        Contact contact = new Contact();
        contact.setId(1L);
        contact.setContactType(ContactType.EMAIL);
        contact.setPersonContact(person);

        when(contactRepository.findById(1L)).thenReturn(Optional.of(contact));

        ContactDTO result = contactService.getOneItem(1L);

        assertThat(result.getContactType()).isEqualTo(ContactType.EMAIL);
    }

    @Test
    void testOneItemNotFound() {
        Long nonExistentId = 1L;
        when(contactRepository.findById(nonExistentId)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> contactService.getOneItem(nonExistentId));

        assertEquals("Contact not found with ID: " + nonExistentId, exception.getMessage());
    }
}
