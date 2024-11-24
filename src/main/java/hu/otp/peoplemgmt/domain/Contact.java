package hu.otp.peoplemgmt.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import hu.otp.peoplemgmt.domain.enumeration.ContactType;
import jakarta.persistence.*;

/**
 * Represents a Contact entity.
 * @author Andras Nyilas
 */
@Entity
@Table(name = "contact")
public class Contact extends Audit {

    /**
     * ID for the Contact.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The type of Contact.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "contact_type", length = 100, nullable = false)
    private ContactType contactType;

    /**
     * The value of the Contact.
     */
    @Column(name = "contact_value", length = 600, nullable = false)
    private String contactValue;

    /**
     * The person associated with this Contact.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id", nullable = false)
    @JsonIgnore
    private Person personContact;

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
     * Gets the person associated with this Contact.
     * @return the person associated with the Contact
     */
    public Person getPersonContact() {
        return personContact;
    }

    /**
     * Sets the person associated with this Contact.
     * @param personContact the new Person associated with the Contact
     */
    public void setPersonContact(Person personContact) {
        this.personContact = personContact;
    }
}
