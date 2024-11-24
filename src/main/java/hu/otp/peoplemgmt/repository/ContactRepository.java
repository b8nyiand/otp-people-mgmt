package hu.otp.peoplemgmt.repository;

import hu.otp.peoplemgmt.domain.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository of Contacts.
 * @author Andras Nyilas
 */
public interface ContactRepository extends JpaRepository<Contact, Long> {

}
