package hu.otp.peoplemgmt.repository;

import hu.otp.peoplemgmt.domain.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository of Addresses.
 * @author Andras Nyilas
 */
public interface AddressRepository extends JpaRepository<Address, Long> {

    /**
     * Retrieves a list of addresses based on the desired person's ID.
     * @param personId the desired persons ID
     * @return a list of Addresses belonging to the desired Person
     */
    List<Address> findByPersonAddress_Id(String personId);

}
