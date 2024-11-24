package hu.otp.peoplemgmt.controller;

import hu.otp.peoplemgmt.domain.dto.PersonDTO;
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

import javax.sql.DataSource;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PersonControllerIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private DataSource dataSource;

    @Test
    @Order(1)
    void testAddPerson() {
        PersonDTO newPerson = new PersonDTO();
        newPerson.setId("jkovacs");
        newPerson.setFirstName("Janos");
        newPerson.setLastName("Kovacs");
        newPerson.setBirthDate(LocalDate.of(1990, 1, 1));

        ResponseEntity<PersonDTO> response = restTemplate.postForEntity("/person/add", newPerson, PersonDTO.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getId()).isEqualTo("jkovacs");
    }

    @Test
    @Order(2)
    void testUpdatePerson() {
        PersonDTO updatedPerson = new PersonDTO();
        updatedPerson.setId("jkovacs");
        updatedPerson.setFirstName("Jolan");
        updatedPerson.setLastName("Kovacs");
        updatedPerson.setBirthDate(LocalDate.of(1990, 1, 1));

        ResponseEntity<PersonDTO> response = restTemplate.exchange("/person/update", HttpMethod.PUT, new HttpEntity<>(updatedPerson), PersonDTO.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getFirstName()).isEqualTo("Jolan");
    }

    @Test
    @Order(3)
    void testListPersons() {
        ResponseEntity<List> response = restTemplate.getForEntity("/person/list-items", List.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotEmpty();
    }

    @Test
    @Order(4)
    void testDeletePerson() {
        restTemplate.delete("/person/delete/jkovacs");

        ResponseEntity<PersonDTO> response = restTemplate.getForEntity("/person/find/jkovacs", PersonDTO.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

}
