package hu.otp.peoplemgmt.controller;

import hu.otp.peoplemgmt.domain.Contact;
import hu.otp.peoplemgmt.domain.dto.ContactDTO;
import hu.otp.peoplemgmt.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/contact")
public class ContactController {

    @Autowired
    private ContactService contactService;

    @PostMapping("/add")
    public ResponseEntity<ContactDTO> addContact(@RequestBody ContactDTO contactDto) {
        return ResponseEntity.ok(contactService.save(contactDto));
    }

    @PutMapping("/update")
    public ResponseEntity<ContactDTO> updateContact(@RequestBody ContactDTO contactDto) {
        return ResponseEntity.ok(contactService.save(contactDto));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteContact(@PathVariable Long id) {
        contactService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/list-items")
    public ResponseEntity<List<ContactDTO>> listContacts() {
        return ResponseEntity.ok(contactService.listItems());
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<ContactDTO> findOne(@PathVariable Long id) {
        return ResponseEntity.ok(contactService.getOneItem(id));
    }

}
