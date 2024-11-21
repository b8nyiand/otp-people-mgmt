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

    @ManyToOne
    @JoinColumn(name="person_id", nullable=false)
    private Person personAddress;

}
