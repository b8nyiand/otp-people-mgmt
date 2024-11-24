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

    @Test
    @Order(1)
    void testAddAddress() {
        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setZipcode("12345");
        addressDTO.setCity("Test");
        addressDTO.setAddressLine("Test utca 123");
        addressDTO.setType(AddressType.CONTINOUS);
        addressDTO.setPersonId("jkovacs");

        Person person = new Person();
        person.setId("jkovacs");
        person.setFirstName("Janos");
        person.setLastName("Kovacs");
        person.setBirthDate(LocalDate.of(1990, 1, 1));
        personRepository.save(person);

        ResponseEntity<Address> response = restTemplate.postForEntity("/address/add", addressDTO, Address.class);

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

        ResponseEntity<AddressDTO> response = restTemplate.exchange("/address/update", HttpMethod.PUT, new HttpEntity<>(updatedAddress), AddressDTO.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getCity()).isEqualTo("Test2");
    }

    @Test
    @Order(3)
    void testListAddresses() {
        ResponseEntity<List> response = restTemplate.getForEntity("/address/list-items", List.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotEmpty();
    }

    @Test
    @Order(4)
    void testFindAddressesByPersonId() {
        ResponseEntity<List> response = restTemplate.getForEntity("/address/person/jkovacs", List.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotEmpty();
    }

    @Test
    @Order(5)
    void testDeleteAddress() {
        restTemplate.delete("/address/delete/1");

        ResponseEntity<AddressDTO> response = restTemplate.getForEntity("/address/find/1", AddressDTO.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

}