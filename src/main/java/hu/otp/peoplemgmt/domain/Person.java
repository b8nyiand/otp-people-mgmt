package hu.otp.peoplemgmt.domain;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name="person")
public class Person {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @Column(name="first_name", length=250, nullable = false)
    private String firstName;

    @Column(name="last_name", length=250, nullable = false)
    private String lastName;

    @Column(name="birth_date", nullable = false)
    private String birthDate;

    @OneToMany(mappedBy="personAddress")
    private Set<Address> addresses;

    @OneToMany(mappedBy="personContact")
    private Set<Contact> contacts;

}
