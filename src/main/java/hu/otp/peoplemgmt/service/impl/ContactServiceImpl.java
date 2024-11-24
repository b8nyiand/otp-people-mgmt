package hu.otp.peoplemgmt.service.impl;

import hu.otp.peoplemgmt.domain.Contact;
import hu.otp.peoplemgmt.domain.Person;
import hu.otp.peoplemgmt.domain.dto.ContactDTO;
import hu.otp.peoplemgmt.repository.ContactRepository;
import hu.otp.peoplemgmt.repository.PersonRepository;
import hu.otp.peoplemgmt.service.ContactService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

    private static final Logger logger = LogManager.getLogger(ContactServiceImpl.class);

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
        logger.info("Saving contact: {}", contactDTO);

        Contact entity = new Contact();
        if (contactDTO.getId() != null) {
            entity = contactRepository.findById(contactDTO.getId())
                    .orElseThrow(() -> {
                        logger.error("ContactService::save: Contact not found with ID: {}", contactDTO.getId());
                        return new IllegalArgumentException("Contact not found with ID: " + contactDTO.getId());
                    });
        }

        ContactDTO savedContact = toDto(contactRepository.save(toEntity(contactDTO, entity)));
        logger.debug("Contact saved successfully: {}", savedContact);

        return savedContact;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void delete(Long id) {
        logger.info("Deleting contact with ID: {}", id);

        if (!contactRepository.existsById(id)) {
            logger.error("ContactService::delete: Contact not found with ID: {}", id);
            throw new EntityNotFoundException("Contact not found with ID: " + id);
        }

        contactRepository.deleteById(id);
        logger.debug("Contact with ID {} deleted successfully", id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public List<ContactDTO> listItems() {
        logger.info("Fetching all contacts");

        List<ContactDTO> contacts = contactRepository.findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());

        logger.debug("Contacts retrieved: {}", contacts);

        return contacts;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public ContactDTO getOneItem(Long id) {
        logger.info("Fetching contact with ID: {}", id);

        ContactDTO contact = contactRepository.findById(id)
                .map(this::toDto)
                .orElseThrow(() -> {
                    logger.error("ContactService::getOneItem: Contact not found with ID: {}", id);
                    return new EntityNotFoundException("Contact not found with ID: " + id);
                });

        logger.debug("Contact retrieved: {}", contact);

        return contact;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public List<ContactDTO> findByPersonId(String personId) {
        logger.info("Fetching contacts for person ID: {}", personId);

        List<ContactDTO> contacts = contactRepository.findByPersonContact_Id(personId)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());

        logger.debug("Contacts retrieved for person ID {}: {}", personId, contacts);

        return contacts;
    }

    /**
     * Maps a Contact entity to DTO.
     *
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
     *
     * @param contactDTO the DTO to map to entity
     * @param entity     the Contact entity
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
