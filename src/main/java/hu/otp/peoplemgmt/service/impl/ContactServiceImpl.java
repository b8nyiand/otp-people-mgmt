package hu.otp.peoplemgmt.service.impl;

import hu.otp.peoplemgmt.domain.Contact;
import hu.otp.peoplemgmt.domain.Person;
import hu.otp.peoplemgmt.domain.dto.ContactDTO;
import hu.otp.peoplemgmt.repository.ContactRepository;
import hu.otp.peoplemgmt.repository.PersonRepository;
import hu.otp.peoplemgmt.service.ContactService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ContactServiceImpl implements ContactService {

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private PersonRepository personRepository;

    @Override
    @Transactional
    public Contact save(ContactDTO contactDTO) {
        Contact entity = new Contact();
        if (contactDTO.getId() != null) {
            entity = contactRepository.findById(contactDTO.getId())
                    .orElseThrow(() -> new IllegalArgumentException("Contact not found with ID: " + contactDTO.getId()));
        }

        return contactRepository.save(toEntity(contactDTO, entity, contactRepository));
    }

    @Override
    public void delete(Long id) {
        contactRepository.deleteById(id);
    }

    @Override
    public List<ContactDTO> listItems() {
        return contactRepository.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public ContactDTO getOneItem(Long id) {
        return contactRepository.findById(id).map(this::toDto).orElse(null);
    }

    private ContactDTO toDto(Contact contact) {
        ContactDTO dto = new ContactDTO();
        dto.setId(contact.getId());
        dto.setContactType(contact.getContactType());
        dto.setContactValue(contact.getContactValue());
        dto.setPersonId(contact.getPersonContact().getId());
        return dto;
    }

    private Contact toEntity(ContactDTO contactDTO, Contact entity, ContactRepository contactRepository) {
        entity.setContactType(contactDTO.getContactType());
        entity.setContactValue(contactDTO.getContactValue());

        if (contactDTO.getPersonId() != null) {
            Person person = personRepository.findById(contactDTO.getPersonId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid person ID: " + contactDTO.getPersonId()));
            entity.setPersonContact(person);
        }
        return entity;
    }

}
