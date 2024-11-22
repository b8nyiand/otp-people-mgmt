package hu.otp.peoplemgmt.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import hu.otp.peoplemgmt.domain.enumeration.ContactType;
import jakarta.persistence.*;

@Entity
@Table(name = "contact")
public class Contact extends Audit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "contact_type", length = 100, nullable = false)
    private ContactType contactType;

    @Column(name = "contact_value", length = 600, nullable = false)
    private String contactValue;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id", nullable = false)
    @JsonIgnore
    private Person personContact;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ContactType getContactType() {
        return contactType;
    }

    public void setContactType(ContactType contactType) {
        this.contactType = contactType;
    }

    public String getContactValue() {
        return contactValue;
    }

    public void setContactValue(String contactValue) {
        this.contactValue = contactValue;
    }

    public Person getPersonContact() {
        return personContact;
    }

    public void setPersonContact(Person personContact) {
        this.personContact = personContact;
    }
}
