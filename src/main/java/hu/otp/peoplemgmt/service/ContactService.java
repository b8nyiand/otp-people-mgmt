package hu.otp.peoplemgmt.service;

import hu.otp.peoplemgmt.domain.dto.ContactDTO;

import java.util.List;

/**
 * Interface of service of Contacts.
 * @author Andras Nyilas
 */
public interface ContactService {

    /**
     * Creates or updates a Contact.
     * @param contactDto the DTO to create or update
     * @return the DTO of the updated Address
     */
    ContactDTO save(ContactDTO contactDto);

    /**
     * Deletes a Contact.
     * @param id the ID of a Contact.
     */
    void delete(Long id);

    /**
     * Lists all the Contacts.
     * @return a list of Contacts
     */
    List<ContactDTO> listItems();

    /**
     * Retrieves exactly one Contact by the given ID.
     * @param id the ID of a Contact
     * @return the Contact with the desired ID
     */
    ContactDTO getOneItem(Long id);

    /**
     * Retrieves a list of Contacts for a specific Person by their ID.
     * @param personId the ID of the Person whose Contacts are to be retrieved.
     * @return a list of {@link ContactDTO} belonging to the specified Person.
     */
    List<ContactDTO> findByPersonId(String personId);

}
