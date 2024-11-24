package hu.otp.peoplemgmt.controller;

import hu.otp.peoplemgmt.domain.dto.PersonDTO;
import hu.otp.peoplemgmt.service.PersonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller of Persons.
 * @author Andras Nyilas
 */
@RestController
@RequestMapping(path = "/person")
public class PersonController {

    private static final Logger logger = LogManager.getLogger(PersonController.class);

    @Autowired
    private PersonService personService;

    @Operation(summary = "Create a Person", description = "Creates a new Person from a PersonDTO and saves it.")
    @PostMapping("/add")
    public ResponseEntity<PersonDTO> addPerson(@RequestBody PersonDTO personDto) {
        logger.info("Received request to add a person: {}", personDto);
        PersonDTO savedPerson = personService.save(personDto);
        logger.debug("Person saved: {}", savedPerson);
        return ResponseEntity.ok(savedPerson);
    }

    @Operation(summary = "Update a Person", description = "Updates a Person from a PersonDTO and saves it.")
    @PutMapping("/update")
    public ResponseEntity<PersonDTO> updatePerson(@RequestBody PersonDTO personDto) {
        logger.info("Received request to update a person: {}", personDto);
        PersonDTO updatedPerson = personService.save(personDto);
        logger.debug("Person updated: {}", updatedPerson);
        return ResponseEntity.ok(updatedPerson);
    }

    @Operation(summary = "Delete a Person", description = "Deletes a Person. Warning: deletion is physical, not logical!")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deletePerson(
            @Parameter(description = "ID of the Person to delete", required = true)
            @PathVariable String id
    ) {
        logger.info("Received request to delete person with ID: {}", id);
        personService.delete(id);
        logger.debug("Person with ID {} deleted", id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "List Persons", description = "Gets all Persons as a list.")
    @GetMapping("/list-items")
    public ResponseEntity<List<PersonDTO>> listPersons() {
        logger.info("Received request to list all persons");
        List<PersonDTO> persons = personService.listItems();
        logger.debug("Listed persons: {}", persons);
        return ResponseEntity.ok(persons);
    }

    @Operation(summary = "Get one Person", description = "Gets exactly one Person by the given ID.")
    @GetMapping("/find/{id}")
    public ResponseEntity<PersonDTO> findOne(
            @Parameter(description = "ID of the Person to get", required = true)
            @PathVariable String id
    ) {
        logger.info("Received request to find person with ID: {}", id);
        PersonDTO person = personService.getOneItem(id);
        logger.debug("Found person: {}", person);
        return ResponseEntity.ok(person);
    }

}
