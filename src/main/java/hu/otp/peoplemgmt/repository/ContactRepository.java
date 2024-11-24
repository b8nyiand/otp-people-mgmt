package hu.otp.peoplemgmt.repository;

import hu.otp.peoplemgmt.domain.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository of Contacts.
 * @author Andras Nyilas
 */
public interface ContactRepository extends JpaRepository<Contact, Long> {

    /**
     * Retrieves a list of contacts based on the desired person's ID.
     * @param personId the desired persons ID
     * @return a list of Contacts belonging to the desired Person
     */
    List<Contact> findByPersonContact_Id(String personId);

}
