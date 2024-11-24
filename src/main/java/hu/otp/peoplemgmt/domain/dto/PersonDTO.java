package hu.otp.peoplemgmt.domain.dto;

import java.time.LocalDate;

/**
 * Data Transfer Object for representing a Person.
 * @author Andras Nyilas
 */
public class PersonDTO {

    /**
     * ID for the Person.
     */
    private String id;

    /**
     * The first name of the Person.
     */
    private String firstName;

    /**
     * The last name of the Person.
     */
    private String lastName;

    /**
     * The birth date of the Person.
     */
    private LocalDate birthDate;

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
}
