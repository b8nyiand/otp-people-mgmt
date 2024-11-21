package hu.otp.peoplemgmt.repository;

import hu.otp.peoplemgmt.domain.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {

}
