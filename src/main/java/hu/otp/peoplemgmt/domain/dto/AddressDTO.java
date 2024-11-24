package hu.otp.peoplemgmt.domain.dto;

import hu.otp.peoplemgmt.domain.enumeration.AddressType;

/**
 * Data Transfer Object for representing an Address.
 * @author Andras Nyilas
 * @author Andras Nyilas
 */
public class AddressDTO {

    /**
     * ID for the Address.
     */
    private Long id;

    /**
     * The zipcode of the Address.
     */
    private String zipcode;

    /**
     * The city of the Address.
     */
    private String city;

    /**
     * The Address line.
     */
    private String addressLine;

    /**
     * The type of Address.
     */
    private AddressType type;

    /**
     * The ID of the Person associated with this Address.
     */
    private String personId;

    /**
     * Gets the unique identifier for the Address.
     * @return the ID of the Address
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the unique identifier for the Address.
     * @param id the new ID of the Address
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets the zipcode of the Address.
     * @return the zipcode of the Address
     */
    public String getZipcode() {
        return zipcode;
    }

    /**
     * Sets the zipcode of the Address.
     * @param zipcode the new zipcode of the Address
     */
    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    /**
     * Gets the city of the Address.
     * @return the city of the Address
     */
    public String getCity() {
        return city;
    }

    /**
     * Sets the city of the Address.
     * @param city the new city of the Address
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * Gets the Address line.
     * @return the Address line
     */
    public String getAddressLine() {
        return addressLine;
    }

    /**
     * Sets the Address line.
     * @param addressLine the new Address line
     */
    public void setAddressLine(String addressLine) {
        this.addressLine = addressLine;
    }

    /**
     * Gets the type of Address.
     * @return the type of the Address
     */
    public AddressType getType() {
        return type;
    }

    /**
     * Sets the type of Address.
     * @param type the new type of the Address
     */
    public void setType(AddressType type) {
        this.type = type;
    }

    /**
     * Gets the ID of the Person associated with this Address.
     * @return the Person ID associated with the Address
     */
    public String getPersonId() {
        return personId;
    }

    /**
     * Sets the ID of the Person associated with this Address.
     * @param personId the new Person ID associated with the Address
     */
    public void setPersonId(String personId) {
        this.personId = personId;
    }
}
