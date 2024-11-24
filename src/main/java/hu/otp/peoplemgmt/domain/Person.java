package hu.otp.peoplemgmt.domain;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Set;

/**
 * Represents a Person entity.
 * @author Andras Nyilas
 */
@Entity
@Table(name = "person")
public class Person extends Audit {

    /**
     * ID for the Person.
     */
    @Id
    @Column(name = "id", length = 50, nullable = false)
    private String id;

    /**
     * The first name of the Person.
     */
    @Column(name = "first_name", length = 250, nullable = false)
    private String firstName;

    /**
     * The last name of the Person.
     */
    @Column(name = "last_name", length = 250, nullable = false)
    private String lastName;

    /**
     * The birth date of the Person.
     */
    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    /**
     * The set of Addresses associated with the Person.
     */
    @OneToMany(mappedBy = "personAddress", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Address> addresses;

    /**
     * The set of Contacts associated with the Person.
     */
    @OneToMany(mappedBy = "personContact", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Contact> contacts;

    /**
     * Gets the unique identifier for the Person.
     * @return the ID of the Person
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the unique identifier for the Person.
     * @param id the new ID of the Person
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets the first name of the Person.
     * @return the first name of the Person
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the first name of the Person.
     * @param firstName the new first name of the Person
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Gets the last name of the Person.
     * @return the last name of the Person
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the last name of the Person.
     * @param lastName the new last name of the Person
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Gets the birth date of the Person.
     * @return the birth date of the Person
     */
    public LocalDate getBirthDate() {
        return birthDate;
    }

    /**
     * Sets the birth date of the Person.
     * @param birthDate the new birth date of the Person
     */
    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    /**
     * Gets the set of Addresses associated with the Person.
     * @return the set of Addresses
     */
    public Set<Address> getAddresses() {
        return addresses;
    }

    /**
     * Sets the set of Addresses associated with the Person.
     * @param addresses the new set of Addresses
     */
    public void setAddresses(Set<Address> addresses) {
        this.addresses = addresses;
    }

    /**
     * Gets the set of Contacts associated with the Person.
     * @return the set of Contacts
     */
    public Set<Contact> getContacts() {
        return contacts;
    }

    /**
     * Sets the set of Contacts associated with the Person.
     * @param contacts the new set of Contacts
     */
    public void setContacts(Set<Contact> contacts) {
        this.contacts = contacts;
    }
}
