package hu.otp.peoplemgmt.service;

import hu.otp.peoplemgmt.domain.Contact;
import hu.otp.peoplemgmt.domain.Person;
import hu.otp.peoplemgmt.domain.dto.ContactDTO;
import hu.otp.peoplemgmt.domain.enumeration.ContactType;
import hu.otp.peoplemgmt.repository.ContactRepository;
import hu.otp.peoplemgmt.repository.PersonRepository;
import hu.otp.peoplemgmt.service.impl.ContactServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ContactServiceTest {

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

    @Test
    void testSaveNewContact() {
        ContactDTO contactDTO = new ContactDTO();
        contactDTO.setContactType(ContactType.EMAIL);
        contactDTO.setContactValue("test@test.com");
        contactDTO.setPersonId("jkovacs");

        Person person = new Person();
        person.setId("jkovacs");

        when(personRepository.findById("jkovacs")).thenReturn(Optional.of(person));

        Contact savedContact = new Contact();
        savedContact.setId(1L);
        when(contactRepository.save(any(Contact.class))).thenReturn(savedContact);

        Contact result = contactService.save(contactDTO);

        assertThat(result.getId()).isEqualTo(1L);
        verify(contactRepository, times(1)).save(any(Contact.class));
    }

    @Test
    void testUpdateExistingContact() {
        ContactDTO contactDTO = new ContactDTO();
        contactDTO.setId(1L);
        contactDTO.setContactType(ContactType.TELEPHONE);
        contactDTO.setContactValue("+36301234567");

        Contact existingContact = new Contact();
        existingContact.setId(1L);
        existingContact.setContactType(ContactType.EMAIL);
        existingContact.setContactValue("test@test.com");
        when(contactRepository.findById(1L)).thenReturn(Optional.of(existingContact));

        Contact updatedContact = new Contact();
        updatedContact.setId(1L);
        updatedContact.setContactType(ContactType.TELEPHONE);
        updatedContact.setContactValue("+36301234567");

        when(contactRepository.save(any(Contact.class))).thenReturn(updatedContact);

        Contact result = contactService.save(contactDTO);

        assertThat(result.getContactValue()).isEqualTo("+36301234567");
        assertThat(result.getContactType()).isEqualTo(ContactType.TELEPHONE);

        verify(contactRepository, times(1)).findById(1L);
        verify(contactRepository, times(1)).save(any(Contact.class));
    }

    @Test
    void testThrowExceptionIfPersonNotFound() {
        ContactDTO contactDTO = new ContactDTO();
        contactDTO.setPersonId("invalid");

        when(personRepository.existsById("invalid")).thenReturn(false);

        assertThrows(IllegalArgumentException.class, () -> contactService.save(contactDTO));
        verify(contactRepository, never()).save(any(Contact.class));
    }

    @Test
    void testDelete() {
        contactService.delete(1L);

        verify(contactRepository, times(1)).deleteById(1L);
    }

    @Test
    void testListItems() {
        Person person = new Person();
        person.setId("jkovacs");
        person.setFirstName("Janos");

        Contact contact = new Contact();
        contact.setId(1L);
        contact.setContactType(ContactType.EMAIL);
        contact.setPersonContact(person);

        when(contactRepository.findAll()).thenReturn(List.of(contact));

        List<ContactDTO> result = contactService.listItems();

        assertThat(result.get(0).getContactType()).isEqualTo(ContactType.EMAIL);
    }

    @Test
    void testGetOneItem() {
        Person person = new Person();
        person.setId("jkovacs");
        person.setFirstName("Janos");

        Contact contact = new Contact();
        contact.setId(1L);
        contact.setContactType(ContactType.EMAIL);
        contact.setPersonContact(person);

        when(contactRepository.findById(1L)).thenReturn(Optional.of(contact));

        ContactDTO result = contactService.getOneItem(1L);

        assertThat(result.getContactType()).isEqualTo(ContactType.EMAIL);
    }

    @Test
    void testGetOneItemNullIfNotFound() {
        when(contactRepository.findById(1L)).thenReturn(Optional.empty());

        ContactDTO result = contactService.getOneItem(1L);
        assertThat(result).isNull();
    }

}
