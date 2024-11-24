package hu.otp.peoplemgmt.repository;

import hu.otp.peoplemgmt.domain.Person;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository of Persons.
 * @author Andras Nyilas
 */
public interface PersonRepository extends JpaRepository<Person, String> {

}
