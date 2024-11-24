package hu.otp.peoplemgmt.controller;

import hu.otp.peoplemgmt.domain.dto.ContactDTO;
import hu.otp.peoplemgmt.service.ContactService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller of Contacts.
 */
@RestController
@RequestMapping(path = "/contact")
public class ContactController {

    private static final Logger logger = LogManager.getLogger(ContactController.class);

    @Autowired
    private ContactService contactService;

    @Operation(summary = "Create a Contact", description = "Creates a new Contact of a Person from a ContactDTO and saves it.")
    @PostMapping("/add")
    public ResponseEntity<ContactDTO> addContact(@RequestBody ContactDTO contactDto) {
        logger.info("Received request to add contact: {}", contactDto);
        ContactDTO savedContact = contactService.save(contactDto);
        logger.debug("Contact saved: {}", savedContact);
        return ResponseEntity.ok(savedContact);
    }

    @Operation(summary = "Update a Contact", description = "Updates a Contact of a Person from a ContactDTO and saves it.")
    @PutMapping("/update")
    public ResponseEntity<ContactDTO> updateContact(@RequestBody ContactDTO contactDto) {
        logger.info("Received request to update contact: {}", contactDto);
        ContactDTO updatedContact = contactService.save(contactDto);
        logger.debug("Contact updated: {}", updatedContact);
        return ResponseEntity.ok(updatedContact);
    }

    @Operation(summary = "Delete a Contact", description = "Deletes a Contact. Warning: deletion is physical, not logical!")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteContact(
            @Parameter(description = "ID of the Contact to delete", required = true)
            @PathVariable Long id
    ) {
        logger.info("Received request to delete contact with ID: {}", id);
        contactService.delete(id);
        logger.debug("Contact with ID {} deleted", id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "List Contacts", description = "Gets all Contacts as a list.")
    @GetMapping("/list-items")
    public ResponseEntity<List<ContactDTO>> listContacts() {
        logger.info("Received request to list all contacts");
        List<ContactDTO> contacts = contactService.listItems();
        logger.debug("Listed contacts: {}", contacts);
        return ResponseEntity.ok(contacts);
    }

    @Operation(summary = "Get one Contact", description = "Gets exactly one Contact by the given ID.")
    @GetMapping("/find/{id}")
    public ResponseEntity<ContactDTO> findOne(
            @Parameter(description = "ID of the Contact to get", required = true)
            @PathVariable Long id
    ) {
        logger.info("Received request to find contact with ID: {}", id);
        ContactDTO contact = contactService.getOneItem(id);
        logger.debug("Found contact: {}", contact);
        return ResponseEntity.ok(contact);
    }

    @Operation(summary = "Find Contacts by Person ID", description = "Retrieves a list of Contacts belonging to a specific Person.")
    @GetMapping("/person/{personId}")
    public ResponseEntity<List<ContactDTO>> getContactsByPersonId(
            @Parameter(description = "ID of the Person whose Contacts are to be retrieved", required = true)
            @PathVariable String personId
    ) {
        logger.info("Received request to find contacts for person with ID: {}", personId);
        List<ContactDTO> contacts = contactService.findByPersonId(personId);
        logger.debug("Contacts found for person ID {}: {}", personId, contacts);
        return ResponseEntity.ok(contacts);
    }

}
