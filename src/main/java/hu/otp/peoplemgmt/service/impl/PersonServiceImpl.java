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

@Service
@Transactional
public class PersonServiceImpl implements PersonService {

    @Autowired
    private PersonRepository personRepository;

    @Override
    @Transactional
    public Person save(PersonDTO personDto) {
        Person entity = personRepository.findById(personDto.getId())
                .orElseGet(Person::new);
        return personRepository.save(toEntity(personDto, entity));
    }

    @Override
    public void delete(String id) {
        personRepository.deleteById(id);
    }

    @Override
    public List<PersonDTO> listItems() {
        return personRepository.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public PersonDTO getOneItem(String id) {
        if (personRepository.findById(id).isPresent()) {
            return toDto(personRepository.findById(id).get());
        }
        return null;
    }

    private PersonDTO toDto(Person person) {
        PersonDTO dto = new PersonDTO();
        dto.setId(person.getId());
        dto.setFirstName(person.getFirstName());
        dto.setLastName(person.getLastName());
        dto.setBirthDate(person.getBirthDate());
        return dto;
    }

    private Person toEntity(PersonDTO personDTO, Person entity) {
        entity.setId(personDTO.getId());
        entity.setFirstName(personDTO.getFirstName());
        entity.setLastName(personDTO.getLastName());
        entity.setBirthDate(personDTO.getBirthDate());
        return entity;
    }

}
