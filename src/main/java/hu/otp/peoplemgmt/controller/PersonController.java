package hu.otp.peoplemgmt.controller;

import hu.otp.peoplemgmt.domain.dto.PersonDTO;
import hu.otp.peoplemgmt.service.PersonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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

    /**
     * The Person service.
     */
    @Autowired
    private PersonService personService;

    @Operation(summary = "Create a Person", description = "Creates a new Person from a PersonDTO and saves it.")
    @PostMapping("/add")
    public ResponseEntity<PersonDTO> addPerson(@RequestBody PersonDTO personDto) {
        return ResponseEntity.ok(personService.save(personDto));
    }

    @Operation(summary = "Update a Person", description = "Updates a Person from a PersonDTO and saves it.")
    @PutMapping("/update")
    public ResponseEntity<PersonDTO> updatePerson(@RequestBody PersonDTO personDto) {
        return ResponseEntity.ok(personService.save(personDto));
    }

    @Operation(summary = "Delete a Person", description = "Deletes a Person. Warning: deletion is physical, not logical!")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deletePerson(
            @Parameter(description = "ID of the Person to delete", required = true)
            @PathVariable String id
    ) {
        personService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "List Persons", description = "Gets all Persons as a list.")
    @GetMapping("/list-items")
    public ResponseEntity<List<PersonDTO>> listPersons() {
        return ResponseEntity.ok(personService.listItems());
    }

    @Operation(summary = "Get one Person", description = "Gets exactly one Person by the given ID.")
    @GetMapping("/find/{id}")
    public ResponseEntity<PersonDTO> findOne(
            @Parameter(description = "ID of the Person to get", required = true)
            @PathVariable String id
    ) {
        return ResponseEntity.ok(personService.getOneItem(id));
    }

}
