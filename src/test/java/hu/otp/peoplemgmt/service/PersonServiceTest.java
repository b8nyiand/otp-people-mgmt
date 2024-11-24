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
import static org.mockito.Mockito.*;

public class PersonServiceTest {

    @Mock
    private PersonRepository personRepository;

    @InjectMocks
    private PersonServiceImpl personService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveNewPerson() {
        PersonDTO personDTO = new PersonDTO();
        personDTO.setId("jkovacs");
        personDTO.setFirstName("Janos");
        personDTO.setLastName("Kovacs");

        Person savedPerson = new Person();
        savedPerson.setId("jkovacs");

        when(personRepository.save(any(Person.class))).thenReturn(savedPerson);

        PersonDTO result = personService.save(personDTO);

        assertThat(result.getId()).isEqualTo("jkovacs");
        verify(personRepository, times(1)).save(any(Person.class));
    }

    @Test
    void testUpdateExistingPerson() {
        PersonDTO personDTO = new PersonDTO();
        personDTO.setId("akovacs");
        personDTO.setFirstName("Antonia");
        personDTO.setLastName("Kovacs");

        Person existingPerson = new Person();
        existingPerson.setId("akovacs");
        existingPerson.setFirstName("Anton");
        existingPerson.setLastName("Kovacs");

        when(personRepository.findById("akovacs")).thenReturn(Optional.of(existingPerson));
        when(personRepository.save(any(Person.class))).thenAnswer(invocation -> invocation.getArgument(0));

        PersonDTO updatedPerson = personService.save(personDTO);

        assertThat(updatedPerson.getId()).isEqualTo("akovacs");
        assertThat(updatedPerson.getFirstName()).isEqualTo("Antonia");
        assertThat(updatedPerson.getLastName()).isEqualTo("Kovacs");

        verify(personRepository, times(1)).findById("akovacs");
        verify(personRepository, times(1)).save(existingPerson);
    }

    @Test
    void testDelete() {
        String existingId = "jkovacs";
        when(personRepository.existsById(existingId)).thenReturn(true);

        personService.delete(existingId);

        verify(personRepository, times(1)).deleteById(existingId);
    }

    @Test
    void testDeleteNotFound() {
        String nonExistentId = "invalid";
        when(personRepository.existsById(nonExistentId)).thenReturn(false);

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            personService.delete(nonExistentId);
        });

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

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            personService.getOneItem(nonExistentId);
        });

        assertEquals("Person not found with ID: " + nonExistentId, exception.getMessage());
        verify(personRepository, times(1)).findById(nonExistentId);
    }

}
