package hu.otp.peoplemgmt.domain;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name="person")
public class Person extends Audit {

    @Id
    @Column(name="id", length=50, nullable = false)
    private String id;

    @Column(name="first_name", length=250, nullable = false)
    private String firstName;

    @Column(name="last_name", length=250, nullable = false)
    private String lastName;

    @Column(name="birth_date", nullable = false)
    private LocalDate birthDate;

    @OneToMany(mappedBy="personAddress", fetch = FetchType.LAZY)
    private Set<Address> addresses;

    @OneToMany(mappedBy="personContact", fetch = FetchType.LAZY)
    private Set<Contact> contacts;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public Set<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(Set<Address> addresses) {
        this.addresses = addresses;
    }

    public Set<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(Set<Contact> contacts) {
        this.contacts = contacts;
    }
}
