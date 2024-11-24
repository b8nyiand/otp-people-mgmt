package hu.otp.peoplemgmt.service;

import hu.otp.peoplemgmt.domain.dto.PersonDTO;

import java.util.List;

/**
 * Interface of service of Persons.
 * @author Andras Nyilas
 */
public interface PersonService {

    /**
     * Creates or updates a Person.
     * @param personDto the DTO to create or update
     * @return the DTO of the updated Person
     */
    PersonDTO save(PersonDTO personDto);

    /**
     * Deletes a Person.
     * @param id the ID of a Person.
     */
    void delete(String id);

    /**
     * Lists all the Persons.
     * @return a list of Persons
     */
    List<PersonDTO> listItems();

    /**
     * Retrieves exactly one Person by the given ID.
     * @param id the ID of a Person
     * @return the Person with the desired ID
     */
    PersonDTO getOneItem(String id);

}
