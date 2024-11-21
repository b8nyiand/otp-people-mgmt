package hu.otp.peoplemgmt.domain;

import jakarta.persistence.*;

@Entity
@Table(name="address")
public class Address extends Audit {

    @Column(name="zipcode", length=20, nullable = false)
    private String zipcode;

    @Column(name="city", length=250, nullable = false)
    private String city;

    @Column(name="address_line", length=600, nullable = false)
    private String addressLine;

    @ManyToOne
    @JoinColumn(name="person_id", nullable=false)
    private Person personAddress;

}
