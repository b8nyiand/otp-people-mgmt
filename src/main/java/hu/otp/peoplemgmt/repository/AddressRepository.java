package hu.otp.peoplemgmt.repository;

import hu.otp.peoplemgmt.domain.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {

}
