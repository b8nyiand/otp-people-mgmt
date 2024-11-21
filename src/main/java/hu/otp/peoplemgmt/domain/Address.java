package hu.otp.peoplemgmt.domain;

import hu.otp.peoplemgmt.domain.enumeration.AddressType;
import jakarta.persistence.*;

@Entity
@Table(name="address")
public class Address extends Audit {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @Column(name="zipcode", length=20, nullable = false)
    private String zipcode;

    @Column(name="city", length=250, nullable = false)
    private String city;

    @Column(name="address_line", length=600, nullable = false)
    private String addressLine;

    @Enumerated(EnumType.STRING)
    @Column(name="type")
    private AddressType type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="person_id", nullable=false)
    private Person personAddress;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddressLine() {
        return addressLine;
    }

    public void setAddressLine(String addressLine) {
        this.addressLine = addressLine;
    }

    public AddressType getType() {
        return type;
    }

    public void setType(AddressType type) {
        this.type = type;
    }

    public Person getPersonAddress() {
        return personAddress;
    }

    public void setPersonAddress(Person personAddress) {
        this.personAddress = personAddress;
    }
}
