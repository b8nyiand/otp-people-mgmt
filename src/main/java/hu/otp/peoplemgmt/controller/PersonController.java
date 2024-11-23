package hu.otp.peoplemgmt.controller;

import hu.otp.peoplemgmt.domain.Person;
import hu.otp.peoplemgmt.domain.dto.PersonDTO;
import hu.otp.peoplemgmt.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/person")
public class PersonController {

    @Autowired
    private PersonService personService;

    @PostMapping("/add")
    public ResponseEntity<PersonDTO> addPerson(@RequestBody PersonDTO personDto) {
        return ResponseEntity.ok(personService.save(personDto));
    }

    @PutMapping("/update")
    public ResponseEntity<PersonDTO> updatePerson(@RequestBody PersonDTO personDto) {
        return ResponseEntity.ok(personService.save(personDto));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deletePerson(@PathVariable String id) {
        personService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/list-items")
    public ResponseEntity<List<PersonDTO>> listPersons() {
        return ResponseEntity.ok(personService.listItems());
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<PersonDTO> findOne(@PathVariable String id) {
        return ResponseEntity.ok(personService.getOneItem(id));
    }

}
