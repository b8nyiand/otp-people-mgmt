package hu.otp.peoplemgmt.controller;

import hu.otp.peoplemgmt.domain.Contact;
import hu.otp.peoplemgmt.domain.Person;
import hu.otp.peoplemgmt.domain.dto.ContactDTO;
import hu.otp.peoplemgmt.domain.enumeration.ContactType;
import hu.otp.peoplemgmt.repository.PersonRepository;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ContactControllerIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PersonRepository personRepository;

    private static final String BASE_URL = "/contact";
    private static final String TEST_PERSON_ID = "jkovacs";

    private Person createTestPerson() {
        Person person = new Person();
        person.setId(TEST_PERSON_ID);
        person.setFirstName("Janos");
        person.setLastName("Kovacs");
        person.setBirthDate(LocalDate.of(1990, 1, 1));
        return personRepository.save(person);
    }

    private ContactDTO createTestContactDTO() {
        ContactDTO contactDTO = new ContactDTO();
        contactDTO.setContactType(ContactType.TELEPHONE);
        contactDTO.setContactValue("+123456789");
        contactDTO.setPersonId(TEST_PERSON_ID);
        return contactDTO;
    }

    @Test
    @Order(1)
    void testAddContact() {
        createTestPerson();
        ContactDTO contactDTO = createTestContactDTO();

        ResponseEntity<Contact> response = restTemplate.postForEntity(BASE_URL + "/add", contactDTO, Contact.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @Order(2)
    void testUpdateContact() {
        ContactDTO updatedContact = new ContactDTO();
        updatedContact.setId(1L);
        updatedContact.setContactValue("+987654321");
        updatedContact.setContactType(ContactType.TELEPHONE);
        updatedContact.setPersonId(TEST_PERSON_ID);

        ResponseEntity<ContactDTO> response = restTemplate.exchange(
                BASE_URL + "/update",
                HttpMethod.PUT,
                new HttpEntity<>(updatedContact),
                ContactDTO.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getContactValue()).isEqualTo("+987654321");
    }

    @Test
    @Order(3)
    void testListContacts() {
        ResponseEntity<List> response = restTemplate.getForEntity(BASE_URL + "/list-items", List.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotEmpty();
    }

    @Test
    @Order(4)
    void testFindContactsByPersonId() {
        ResponseEntity<List> response = restTemplate.getForEntity(BASE_URL + "/person/" + TEST_PERSON_ID, List.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotEmpty();
    }

    @Test
    @Order(5)
    void testDeleteContact() {
        restTemplate.delete(BASE_URL + "/delete/1");

        ResponseEntity<ContactDTO> response = restTemplate.getForEntity(BASE_URL + "/find/1", ContactDTO.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}
