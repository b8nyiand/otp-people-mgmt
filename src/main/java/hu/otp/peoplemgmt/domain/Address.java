package hu.otp.peoplemgmt.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import hu.otp.peoplemgmt.domain.enumeration.AddressType;
import jakarta.persistence.*;

/**
 * Represents an Address entity.
 * @author Andras Nyilas
 */
@Entity
@Table(name = "address")
public class Address extends Audit {

    /**
     * ID for the Address.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The zipcode of the Address.
     */
    @Column(name = "zipcode", length = 20, nullable = false)
    private String zipcode;

    /**
     * The city of the Address.
     */
    @Column(name = "city", length = 250, nullable = false)
    private String city;

    /**
     * The Address line.
     */
    @Column(name = "address_line", length = 600, nullable = false)
    private String addressLine;

    /**
     * The type of Address
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private AddressType type;

    /**
     * The person associated with this Address.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id", nullable = false)
    @JsonIgnore
    private Person personAddress;

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
     * Gets the Person associated with this Address.
     * @return the Person associated with the Address
     */
    public Person getPersonAddress() {
        return personAddress;
    }

    /**
     * Sets the Person associated with this Address.
     * @param personAddress the new Person associated with the Address
     */
    public void setPersonAddress(Person personAddress) {
        this.personAddress = personAddress;
    }
}
