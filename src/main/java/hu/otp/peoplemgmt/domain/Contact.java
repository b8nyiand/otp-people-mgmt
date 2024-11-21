package hu.otp.peoplemgmt.domain;

import jakarta.persistence.*;

@Entity
@Table(name="contact")
public class Contact extends Audit {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @Column(name="contact_type", length=100, nullable = false)
    private String contactType;

    @Column(name="contact_value", length=600, nullable = false)
    private String contactValue;

    @ManyToOne
    @JoinColumn(name="person_id", nullable=false)
    private Person personContact;

}
