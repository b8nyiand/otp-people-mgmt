package hu.otp.peoplemgmt.repository;

import hu.otp.peoplemgmt.domain.Address;
import hu.otp.peoplemgmt.domain.Person;
import hu.otp.peoplemgmt.domain.enumeration.AddressType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class AddressRepositoryTest {

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private PersonRepository personRepository;

    @Test
    void testFindByPersonAddressId() {
        Person person = new Person();
        person.setId("ptest");
        person.setFirstName("Test");
        person.setLastName("P");
        person.setBirthDate(LocalDate.now());

        personRepository.save(person);

        Address address = new Address();
        address.setZipcode("12345");
        address.setCity("Test");
        address.setAddressLine("Test utca 123");
        address.setType(AddressType.CONTINOUS);
        address.setPersonAddress(person);

        addressRepository.save(address);

        List<Address> result = addressRepository.findByPersonAddress_Id("ptest");

        assertThat(result).isNotEmpty();
        assertThat(result.get(0).getCity()).isEqualTo("Test");
        assertThat(result.get(0).getAddressLine()).isEqualTo("Test utca 123");
        assertThat(result.get(0).getType()).isEqualTo(AddressType.CONTINOUS);
    }

    @Test
    void testFindByPersonAddressIdNotFound() {
        List<Address> result = addressRepository.findByPersonAddress_Id("999");
        assertThat(result).isEmpty();
    }

}
