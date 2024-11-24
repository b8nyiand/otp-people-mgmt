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

/**
 * Implementation of ContactService.
 * @author Andras Nyilas
 */
@Service
@Transactional
public class ContactServiceImpl implements ContactService {

    /**
     * The repository of Contacts.
     */
    @Autowired
    private ContactRepository contactRepository;

    /**
     * The repository of Persons.
     */
    @Autowired
    private PersonRepository personRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public ContactDTO save(ContactDTO contactDTO) {
        Contact entity = new Contact();
        if (contactDTO.getId() != null) {
            entity = contactRepository.findById(contactDTO.getId())
                    .orElseThrow(() -> new IllegalArgumentException("Contact not found with ID: " + contactDTO.getId()));
        }

        return toDto(contactRepository.save(toEntity(contactDTO, entity)));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void delete(Long id) {
        contactRepository.deleteById(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public List<ContactDTO> listItems() {
        return contactRepository.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public ContactDTO getOneItem(Long id) {
        return contactRepository.findById(id).map(this::toDto).orElse(null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public List<ContactDTO> findByPersonId(String personId) {
        return contactRepository.findByPersonContact_Id(personId)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Maps a Contact entity to DTO.
     * @param contact the entity to map to DTO
     * @return a DTO mapped from the Contact entity
     */
    private ContactDTO toDto(Contact contact) {
        ContactDTO dto = new ContactDTO();
        dto.setId(contact.getId());
        dto.setContactType(contact.getContactType());
        dto.setContactValue(contact.getContactValue());
        dto.setPersonId(contact.getPersonContact().getId());
        return dto;
    }

    /**
     * Maps a ContactDTO to a Contact entity.
     * @param contactDTO the DTO to map to entity
     * @param entity the Contact entity
     * @return an entity mapped from the given ContactDTO
     */
    private Contact toEntity(ContactDTO contactDTO, Contact entity) {
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
