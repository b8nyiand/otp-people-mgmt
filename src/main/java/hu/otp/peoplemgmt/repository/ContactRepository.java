package hu.otp.peoplemgmt.repository;

import hu.otp.peoplemgmt.domain.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactRepository extends JpaRepository<Contact, Long> {

}
