package hu.otp.peoplemgmt.service;

import hu.otp.peoplemgmt.domain.Person;
import hu.otp.peoplemgmt.domain.dto.PersonDTO;
import hu.otp.peoplemgmt.repository.PersonRepository;
import hu.otp.peoplemgmt.service.impl.PersonServiceImpl;
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

class PersonServiceTest {

    @Mock
    private PersonRepository personRepository;

    @InjectMocks
    private PersonServiceImpl personService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private PersonDTO createPersonDTO(String id, String firstName, String lastName) {
        PersonDTO personDTO = new PersonDTO();
        personDTO.setId(id);
        personDTO.setFirstName(firstName);
        personDTO.setLastName(lastName);
        return personDTO;
    }

    @Test
    void testSaveNewPerson() {
        PersonDTO personDTO = createPersonDTO("jkovacs", "Janos", "Kovacs");

        when(personRepository.save(any(Person.class))).thenAnswer(invocation -> {
            Person person = invocation.getArgument(0);
            person.setId(personDTO.getId());
            return person;
        });

        PersonDTO result = personService.save(personDTO);

        assertThat(result.getId()).isEqualTo("jkovacs");
        verify(personRepository).save(any(Person.class));
    }

    @Test
    void testUpdateExistingPerson() {
        PersonDTO personDTO = createPersonDTO("akovacs", "Antonia", "Kovacs");

        Person existingPerson = new Person();
        existingPerson.setId("akovacs");
        existingPerson.setFirstName("Anton");
        existingPerson.setLastName("Kovacs");

        when(personRepository.findById("akovacs")).thenReturn(Optional.of(existingPerson));
        when(personRepository.save(any(Person.class))).thenAnswer(invocation -> invocation.getArgument(0));

        PersonDTO result = personService.save(personDTO);

        assertThat(result.getFirstName()).isEqualTo("Antonia");
        assertThat(result.getLastName()).isEqualTo("Kovacs");
        verify(personRepository).save(any(Person.class));
    }

    @Test
    void testDelete() {
        String existingId = "jkovacs";
        when(personRepository.existsById(existingId)).thenReturn(true);

        personService.delete(existingId);

        verify(personRepository).deleteById(existingId);
    }

    @Test
    void testDeleteNotFound() {
        String nonExistentId = "invalid";
        when(personRepository.existsById(nonExistentId)).thenReturn(false);

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> personService.delete(nonExistentId));

        assertEquals("Person not found with ID: " + nonExistentId, exception.getMessage());
        verify(personRepository, never()).deleteById(anyString());
    }

    @Test
    void testListItems() {
        Person person = new Person();
        person.setId("jkovacs");
        person.setFirstName("Janos");

        when(personRepository.findAll()).thenReturn(List.of(person));

        List<PersonDTO> result = personService.listItems();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getFirstName()).isEqualTo("Janos");
    }

    @Test
    void testGetOneItem() {
        Person person = new Person();
        person.setId("jkovacs");
        person.setFirstName("Janos");

        when(personRepository.findById("jkovacs")).thenReturn(Optional.of(person));

        PersonDTO result = personService.getOneItem("jkovacs");

        assertThat(result.getFirstName()).isEqualTo("Janos");
    }

    @Test
    void testOneItemNotFound() {
        String nonExistentId = "invalid";
        when(personRepository.findById(nonExistentId)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> personService.getOneItem(nonExistentId));

        assertEquals("Person not found with ID: " + nonExistentId, exception.getMessage());
    }
}
