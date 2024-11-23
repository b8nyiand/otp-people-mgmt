package hu.otp.peoplemgmt.service;

import hu.otp.peoplemgmt.domain.Address;
import hu.otp.peoplemgmt.domain.Person;
import hu.otp.peoplemgmt.domain.dto.AddressDTO;
import hu.otp.peoplemgmt.domain.enumeration.AddressType;
import hu.otp.peoplemgmt.repository.AddressRepository;
import hu.otp.peoplemgmt.repository.PersonRepository;
import hu.otp.peoplemgmt.service.impl.AddressServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class AddressServiceTest {

    @Mock
    private AddressRepository addressRepository;

    @Mock
    private PersonRepository personRepository;

    @InjectMocks
    private AddressServiceImpl addressService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveNewAddress() {
        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setZipcode("12345");
        addressDTO.setCity("Test");
        addressDTO.setAddressLine("Test utca 123");
        addressDTO.setType(AddressType.CONTINOUS);
        addressDTO.setPersonId("jkovacs");

        Person person = new Person();
        person.setId("jkovacs");

        when(personRepository.existsById("jkovacs")).thenReturn(true);
        when(personRepository.findById("jkovacs")).thenReturn(Optional.of(person));

        Address savedAddress = new Address();
        savedAddress.setId(1L);
        when(addressRepository.save(any(Address.class))).thenReturn(savedAddress);

        Address result = addressService.save(addressDTO);

        assertThat(result.getId()).isEqualTo(1L);
        verify(addressRepository, times(1)).save(any(Address.class));
    }

    @Test
    void testUpdateExistingAddress() {
        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setId(1L);
        addressDTO.setZipcode("12345");
        addressDTO.setCity("Test2");
        addressDTO.setAddressLine("Test2 utca 123");
        addressDTO.setType(AddressType.TEMPORARY);

        Address existingAddress = new Address();
        existingAddress.setId(1L);
        when(addressRepository.findById(1L)).thenReturn(Optional.of(existingAddress));

        Address updatedAddress = new Address();
        updatedAddress.setId(1L);
        updatedAddress.setCity("Test2");

        when(addressRepository.save(any(Address.class))).thenReturn(updatedAddress);

        Address result = addressService.save(addressDTO);

        assertThat(result.getCity()).isEqualTo("Test2");

        verify(addressRepository, times(1)).findById(1L);
        verify(addressRepository, times(1)).save(any(Address.class));
    }

    @Test
    void testThrowExceptionIfPersonNotFound() {
        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setPersonId("invalid");

        when(personRepository.existsById("invalid")).thenReturn(false);

        assertThrows(IllegalArgumentException.class, () -> addressService.save(addressDTO));
        verify(addressRepository, never()).save(any(Address.class));
    }

    @Test
    void testDelete() {
        addressService.delete(1L);

        verify(addressRepository, times(1)).deleteById(1L);
    }

    @Test
    void testListItems() {
        Person person1 = new Person();
        person1.setId("jkovacs");
        person1.setFirstName("Janos");

        Address address1 = new Address();
        address1.setId(1L);
        address1.setCity("Test1");
        address1.setPersonAddress(person1);

        Person person2 = new Person();
        person2.setId("jokovacs");
        person2.setFirstName("Jolan");

        Address address2 = new Address();
        address2.setId(2L);
        address2.setCity("Test2");
        address2.setPersonAddress(person2);

        when(addressRepository.findAll()).thenReturn(List.of(address1, address2));

        List<AddressDTO> result = addressService.listItems();

        assertThat(result.get(0).getCity()).isEqualTo("Test1");
        assertThat(result.get(1).getCity()).isEqualTo("Test2");
    }

    @Test
    void testGetOneItem() {
        Person person = new Person();
        person.setId("jokovacs");
        person.setFirstName("Jolan");

        Address address = new Address();
        address.setId(1L);
        address.setCity("Test");
        address.setPersonAddress(person);

        when(addressRepository.findById(1L)).thenReturn(Optional.of(address));

        AddressDTO result = addressService.getOneItem(1L);
        assertThat(result.getCity()).isEqualTo("Test");
    }

    @Test
    void testGetOneItemNullIfNotFound() {
        when(addressRepository.findById(1L)).thenReturn(Optional.empty());

        AddressDTO result = addressService.getOneItem(1L);
        assertThat(result).isNull();
    }

}
