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
class PersonControllerIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private DataSource dataSource;

    private static final String BASE_URL = "/person";
    private static final String TEST_PERSON_ID = "jkovacs";

    private PersonDTO createTestPersonDTO(String firstName, String lastName) {
        PersonDTO personDTO = new PersonDTO();
        personDTO.setId(TEST_PERSON_ID);
        personDTO.setFirstName(firstName);
        personDTO.setLastName(lastName);
        personDTO.setBirthDate(LocalDate.of(1990, 1, 1));
        return personDTO;
    }

    @Test
    @Order(1)
    void testAddPerson() {
        PersonDTO newPerson = createTestPersonDTO("Janos", "Kovacs");

        ResponseEntity<PersonDTO> response = restTemplate.postForEntity(BASE_URL + "/add", newPerson, PersonDTO.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isEqualTo(TEST_PERSON_ID);
    }

    @Test
    @Order(2)
    void testUpdatePerson() {
        PersonDTO updatedPerson = createTestPersonDTO("Jolan", "Kovacs");

        ResponseEntity<PersonDTO> response = restTemplate.exchange(
                BASE_URL + "/update",
                HttpMethod.PUT,
                new HttpEntity<>(updatedPerson),
                PersonDTO.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getFirstName()).isEqualTo("Jolan");
    }

    @Test
    @Order(3)
    void testListPersons() {
        ResponseEntity<List> response = restTemplate.getForEntity(BASE_URL + "/list-items", List.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotEmpty();
    }

    @Test
    @Order(4)
    void testDeletePerson() {
        restTemplate.delete(BASE_URL + "/delete/" + TEST_PERSON_ID);

        ResponseEntity<PersonDTO> response = restTemplate.getForEntity(BASE_URL + "/find/" + TEST_PERSON_ID, PersonDTO.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}
