package hu.otp.peoplemgmt.repository;

import hu.otp.peoplemgmt.domain.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Long> {

    List<Address> findByPersonAddress_Id(String personId);

}
