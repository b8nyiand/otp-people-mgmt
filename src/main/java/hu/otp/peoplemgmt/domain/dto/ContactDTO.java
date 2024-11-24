package hu.otp.peoplemgmt.domain.dto;

import hu.otp.peoplemgmt.domain.enumeration.ContactType;

/**
 * Data Transfer Object for representing a Contact.
 * @author Andras Nyilas
 */
public class ContactDTO {

    /**
     * ID for the Contact.
     */
    private Long id;

    /**
     * The type of Contact.
     */
    private ContactType contactType;

    /**
     * The value of the Contact.
     */
    private String contactValue;

    /**
     * The ID of the Person associated with this Contact.
     */
    private String personId;

    /**
     * Gets the unique identifier for the Contact.
     * @return the ID of the Contact
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the unique identifier for the Contact.
     * @param id the new ID of the Contact
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets the type of Contact.
     * @return the type of the Contact
     */
    public ContactType getContactType() {
        return contactType;
    }

    /**
     * Sets the type of Contact.
     * @param contactType the new type of the Contact
     */
    public void setContactType(ContactType contactType) {
        this.contactType = contactType;
    }

    /**
     * Gets the value of the Contact.
     * @return the value of the Contact
     */
    public String getContactValue() {
        return contactValue;
    }

    /**
     * Sets the value of the Contact.
     * @param contactValue the new value of the Contact
     */
    public void setContactValue(String contactValue) {
        this.contactValue = contactValue;
    }

    /**
     * Gets the ID of the Person associated with this Contact.
     * @return the Person ID associated with the Contact
     */
    public String getPersonId() {
        return personId;
    }

    /**
     * Sets the ID of the Person associated with this Contact.
     * @param personId the new Person ID associated with the Contact
     */
    public void setPersonId(String personId) {
        this.personId = personId;
    }
}
