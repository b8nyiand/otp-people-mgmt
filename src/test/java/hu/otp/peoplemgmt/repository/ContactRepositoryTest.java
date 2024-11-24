package hu.otp.peoplemgmt.repository;

import hu.otp.peoplemgmt.domain.Contact;
import hu.otp.peoplemgmt.domain.Person;
import hu.otp.peoplemgmt.domain.enumeration.ContactType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class ContactRepositoryTest {

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private PersonRepository personRepository;

    @Test
    void testFindByPersonContactId() {
        Person person = new Person();
        person.setId("jkovacs");
        person.setFirstName("Janos");
        person.setLastName("Kovacs");
        person.setBirthDate(LocalDate.now());
        personRepository.save(person);

        Contact contact = new Contact();
        contact.setContactType(ContactType.EMAIL);
        contact.setContactValue("test@test.com");
        contact.setPersonContact(person);
        contactRepository.save(contact);

        List<Contact> result = contactRepository.findByPersonContact_Id("jkovacs");

        assertThat(result).isNotEmpty();
        assertThat(result.get(0).getContactType()).isEqualTo(ContactType.EMAIL);
        assertThat(result.get(0).getContactValue()).isEqualTo("test@test.com");
    }

    @Test
    void testFindByPersonContactIdNotFound() {
        List<Contact> result = contactRepository.findByPersonContact_Id("invalid");
        assertThat(result).isEmpty();
    }
}