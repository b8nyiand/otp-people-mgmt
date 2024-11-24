package hu.otp.peoplemgmt.service.impl;

import hu.otp.peoplemgmt.domain.Person;
import hu.otp.peoplemgmt.domain.dto.PersonDTO;
import hu.otp.peoplemgmt.repository.PersonRepository;
import hu.otp.peoplemgmt.service.PersonService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of PersonService.
 * @author Andras Nyilas
 */
@Service
@Transactional
public class PersonServiceImpl implements PersonService {

    private static final Logger logger = LogManager.getLogger(PersonServiceImpl.class);

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
    public PersonDTO save(PersonDTO personDto) {
        logger.info("Saving person: {}", personDto);

        Person entity = personRepository.findById(personDto.getId())
                .orElseGet(Person::new);

        PersonDTO savedPerson = toDto(personRepository.save(toEntity(personDto, entity)));
        logger.debug("Person saved successfully: {}", savedPerson);

        return savedPerson;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void delete(String id) {
        logger.info("Deleting person with ID: {}", id);

        if (!personRepository.existsById(id)) {
            logger.error("Person not found with ID: {}", id);
            throw new EntityNotFoundException("Person not found with ID: " + id);
        }

        personRepository.deleteById(id);
        logger.debug("Person with ID {} deleted successfully", id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public List<PersonDTO> listItems() {
        logger.info("Fetching all persons");

        List<PersonDTO> persons = personRepository.findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());

        logger.debug("Persons retrieved: {}", persons);

        return persons;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public PersonDTO getOneItem(String id) {
        logger.info("Fetching person with ID: {}", id);

        PersonDTO person = personRepository.findById(id)
                .map(this::toDto)
                .orElseThrow(() -> {
                    logger.error("Person not found with ID: {}", id);
                    return new EntityNotFoundException("Person not found with ID: " + id);
                });

        logger.debug("Person retrieved: {}", person);

        return person;
    }

    /**
     * Maps a Person entity to DTO.
     *
     * @param person the entity to map to DTO
     * @return a DTO mapped from the Person entity
     */
    private PersonDTO toDto(Person person) {
        PersonDTO dto = new PersonDTO();
        dto.setId(person.getId());
        dto.setFirstName(person.getFirstName());
        dto.setLastName(person.getLastName());
        dto.setBirthDate(person.getBirthDate());
        return dto;
    }

    /**
     * Maps a PersonDTO to a Person entity.
     *
     * @param personDTO the DTO to map to entity
     * @param entity    the Person entity
     * @return an entity mapped from the given PersonDTO
     */
    private Person toEntity(PersonDTO personDTO, Person entity) {
        entity.setId(personDTO.getId());
        entity.setFirstName(personDTO.getFirstName());
        entity.setLastName(personDTO.getLastName());
        entity.setBirthDate(personDTO.getBirthDate());
        return entity;
    }

}
