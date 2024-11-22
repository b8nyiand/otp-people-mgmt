package hu.otp.peoplemgmt.service;

import hu.otp.peoplemgmt.domain.Person;
import hu.otp.peoplemgmt.domain.dto.PersonDTO;

import java.util.List;

public interface PersonService {

    Person save(PersonDTO personDto);

    void delete(String id);

    List<PersonDTO> listItems();

    PersonDTO getOneItem(String id);

}
