package hu.otp.peoplemgmt.service.impl;

import hu.otp.peoplemgmt.domain.Person;
import hu.otp.peoplemgmt.domain.dto.PersonDTO;
import hu.otp.peoplemgmt.repository.PersonRepository;
import hu.otp.peoplemgmt.service.PersonService;
import jakarta.transaction.Transactional;
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
        Person entity = personRepository.findById(personDto.getId())
                .orElseGet(Person::new);
        return toDto(personRepository.save(toEntity(personDto, entity)));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(String id) {
        personRepository.deleteById(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<PersonDTO> listItems() {
        return personRepository.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PersonDTO getOneItem(String id) {
        return personRepository.findById(id).map(this::toDto).orElse(null);
    }

    /**
     * Maps a Person entity to DTO.
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
     * @param personDTO the DTO to map to entity
     * @param entity the Person entity
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
