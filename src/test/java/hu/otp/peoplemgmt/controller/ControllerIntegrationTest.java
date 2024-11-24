package hu.otp.peoplemgmt.controller;

import hu.otp.peoplemgmt.domain.Address;
import hu.otp.peoplemgmt.domain.Contact;
import hu.otp.peoplemgmt.domain.Person;
import hu.otp.peoplemgmt.domain.dto.AddressDTO;
import hu.otp.peoplemgmt.domain.dto.ContactDTO;
import hu.otp.peoplemgmt.domain.dto.PersonDTO;
import hu.otp.peoplemgmt.domain.enumeration.AddressType;
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
class ControllerIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PersonRepository personRepository;

    private static final String ADDRESS_BASE_URL = "/address";
    private static final String CONTACT_BASE_URL = "/contact";
    private static final String PERSON_BASE_URL = "/person";
    private static final String TEST_PERSON_ID = "jakovacs";
    private static final String TEST_OTHER_PERSON_ID = "jakovacs";

    private Person createTestPerson() {
        Person person = new Person();
        person.setId(TEST_PERSON_ID);
        person.setFirstName("Janos");
        person.setLastName("Kovacs");
        person.setBirthDate(LocalDate.of(1990, 1, 1));
        return personRepository.save(person);
    }

    private AddressDTO createTestAddressDTO() {
        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setZipcode("12345");
        addressDTO.setCity("Test");
        addressDTO.setAddressLine("Test utca 123");
        addressDTO.setType(AddressType.CONTINOUS);
        addressDTO.setPersonId(TEST_PERSON_ID);
        return addressDTO;
    }

    private ContactDTO createTestContactDTO() {
        ContactDTO contactDTO = new ContactDTO();
        contactDTO.setContactType(ContactType.TELEPHONE);
        contactDTO.setContactValue("+123456789");
        contactDTO.setPersonId(TEST_PERSON_ID);
        return contactDTO;
    }

    private PersonDTO createTestPersonDTO(String firstName, String lastName) {
        PersonDTO personDTO = new PersonDTO();
        personDTO.setId(TEST_OTHER_PERSON_ID);
        personDTO.setFirstName(firstName);
        personDTO.setLastName(lastName);
        personDTO.setBirthDate(LocalDate.of(1990, 1, 1));
        return personDTO;
    }

    @Test
    @Order(1)
    void testAddAddress() {
        createTestPerson();
        AddressDTO addressDTO = createTestAddressDTO();

        ResponseEntity<Address> response = restTemplate.postForEntity(ADDRESS_BASE_URL + "/add", addressDTO, Address.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @Order(2)
    void testUpdateAddress() {
        AddressDTO updatedAddress = new AddressDTO();
        updatedAddress.setId(1L);
        updatedAddress.setCity("Test2");
        updatedAddress.setZipcode("54321");
        updatedAddress.setAddressLine("Test2 utca 123");
        updatedAddress.setType(AddressType.CONTINOUS);

        ResponseEntity<AddressDTO> response = restTemplate.exchange(
                ADDRESS_BASE_URL + "/update",
                HttpMethod.PUT,
                new HttpEntity<>(updatedAddress),
                AddressDTO.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getCity()).isEqualTo("Test2");
    }

    @Test
    @Order(3)
    void testListAddresses() {
        ResponseEntity<List> response = restTemplate.getForEntity(ADDRESS_BASE_URL + "/list-items", List.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotEmpty();
    }

    @Test
    @Order(4)
    void testFindAddressesByPersonId() {
        ResponseEntity<List> response = restTemplate.getForEntity(ADDRESS_BASE_URL + "/person/" + TEST_PERSON_ID, List.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotEmpty();
    }

    @Test
    @Order(5)
    void testDeleteAddress() {
        restTemplate.delete(ADDRESS_BASE_URL + "/delete/1");

        ResponseEntity<AddressDTO> response = restTemplate.getForEntity(ADDRESS_BASE_URL + "/find/1", AddressDTO.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    @Order(6)
    void testAddContact() {
        ContactDTO contactDTO = createTestContactDTO();

        ResponseEntity<Contact> response = restTemplate.postForEntity(CONTACT_BASE_URL + "/add", contactDTO, Contact.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @Order(7)
    void testUpdateContact() {
        ContactDTO updatedContact = new ContactDTO();
        updatedContact.setId(1L);
        updatedContact.setContactValue("+987654321");
        updatedContact.setContactType(ContactType.TELEPHONE);
        updatedContact.setPersonId(TEST_PERSON_ID);

        ResponseEntity<ContactDTO> response = restTemplate.exchange(
                CONTACT_BASE_URL + "/update",
                HttpMethod.PUT,
                new HttpEntity<>(updatedContact),
                ContactDTO.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getContactValue()).isEqualTo("+987654321");
    }

    @Test
    @Order(8)
    void testListContacts() {
        ResponseEntity<List> response = restTemplate.getForEntity(CONTACT_BASE_URL + "/list-items", List.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotEmpty();
    }

    @Test
    @Order(9)
    void testFindContactsByPersonId() {
        ResponseEntity<List> response = restTemplate.getForEntity(CONTACT_BASE_URL + "/person/" + TEST_PERSON_ID, List.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotEmpty();
    }

    @Test
    @Order(10)
    void testDeleteContact() {
        restTemplate.delete(CONTACT_BASE_URL + "/delete/1");

        ResponseEntity<ContactDTO> response = restTemplate.getForEntity(CONTACT_BASE_URL + "/find/1", ContactDTO.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    @Order(11)
    void testAddPerson() {
        PersonDTO newPerson = createTestPersonDTO("Janos", "Kovacs");

        ResponseEntity<PersonDTO> response = restTemplate.postForEntity(PERSON_BASE_URL + "/add", newPerson, PersonDTO.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isEqualTo(TEST_PERSON_ID);
    }

    @Test
    @Order(12)
    void testUpdatePerson() {
        PersonDTO updatedPerson = createTestPersonDTO("Jolan", "Kovacs");

        ResponseEntity<PersonDTO> response = restTemplate.exchange(
                PERSON_BASE_URL + "/update",
                HttpMethod.PUT,
                new HttpEntity<>(updatedPerson),
                PersonDTO.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getFirstName()).isEqualTo("Jolan");
    }

    @Test
    @Order(13)
    void testListPersons() {
        ResponseEntity<List> response = restTemplate.getForEntity(PERSON_BASE_URL + "/list-items", List.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotEmpty();
    }

    @Test
    @Order(14)
    void testDeletePerson() {
        restTemplate.delete(PERSON_BASE_URL + "/delete/" + TEST_PERSON_ID);

        ResponseEntity<PersonDTO> response = restTemplate.getForEntity(PERSON_BASE_URL + "/find/" + TEST_PERSON_ID, PersonDTO.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

}
