package hu.otp.peoplemgmt.controller;

import hu.otp.peoplemgmt.domain.dto.ContactDTO;
import hu.otp.peoplemgmt.service.ContactService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller of Contacts.
 * @author Andras Nyilas
 */
@RestController
@RequestMapping(path = "/contact")
public class ContactController {

    /**
     * The Contact service.
     */
    @Autowired
    private ContactService contactService;

    @Operation(summary = "Create a Contact", description = "Creates a new Contact of a Person from a ContactDTO and saves to the database.")
    @PostMapping("/add")
    public ResponseEntity<ContactDTO> addContact(@RequestBody ContactDTO contactDto) {
        return ResponseEntity.ok(contactService.save(contactDto));
    }

    @Operation(summary = "Update a Contact", description = "Updates a Contact of a Person from a ContactDTO and saves to the database.")
    @PutMapping("/update")
    public ResponseEntity<ContactDTO> updateContact(@RequestBody ContactDTO contactDto) {
        return ResponseEntity.ok(contactService.save(contactDto));
    }

    @Operation(summary = "Delete a Contact", description = "Deletes a Contact from the database. Warning: deletion is physical, not logical!")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteContact(
            @Parameter(description = "ID of the Contact to delete", required = true)
            @PathVariable Long id
    ) {
        contactService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "List Contacts", description = "Gets all Contacts from the database as a list.")
    @GetMapping("/list-items")
    public ResponseEntity<List<ContactDTO>> listContacts() {
        return ResponseEntity.ok(contactService.listItems());
    }

    @Operation(summary = "Get one Contact", description = "Gets exactly one Contact from the database by the given ID.")
    @GetMapping("/find/{id}")
    public ResponseEntity<ContactDTO> findOne(
            @Parameter(description = "ID of the Contact to get", required = true)
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(contactService.getOneItem(id));
    }

}
