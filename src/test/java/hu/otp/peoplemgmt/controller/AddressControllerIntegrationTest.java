package hu.otp.peoplemgmt.controller;

import hu.otp.peoplemgmt.domain.Address;
import hu.otp.peoplemgmt.domain.Person;
import hu.otp.peoplemgmt.domain.dto.AddressDTO;
import hu.otp.peoplemgmt.domain.enumeration.AddressType;
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
class AddressControllerIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PersonRepository personRepository;

    private static final String BASE_URL = "/address";
    private static final String TEST_PERSON_ID = "jkovacs";

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

    @Test
    @Order(1)
    void testAddAddress() {
        createTestPerson();
        AddressDTO addressDTO = createTestAddressDTO();

        ResponseEntity<Address> response = restTemplate.postForEntity(BASE_URL + "/add", addressDTO, Address.class);

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
                BASE_URL + "/update",
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
        ResponseEntity<List> response = restTemplate.getForEntity(BASE_URL + "/list-items", List.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotEmpty();
    }

    @Test
    @Order(4)
    void testFindAddressesByPersonId() {
        ResponseEntity<List> response = restTemplate.getForEntity(BASE_URL + "/person/" + TEST_PERSON_ID, List.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotEmpty();
    }

    @Test
    @Order(5)
    void testDeleteAddress() {
        restTemplate.delete(BASE_URL + "/delete/1");

        ResponseEntity<AddressDTO> response = restTemplate.getForEntity(BASE_URL + "/find/1", AddressDTO.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}
