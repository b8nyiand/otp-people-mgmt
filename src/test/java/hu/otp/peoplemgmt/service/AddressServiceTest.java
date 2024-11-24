package hu.otp.peoplemgmt.service;

import hu.otp.peoplemgmt.domain.Address;
import hu.otp.peoplemgmt.domain.Person;
import hu.otp.peoplemgmt.domain.dto.AddressDTO;
import hu.otp.peoplemgmt.domain.enumeration.AddressType;
import hu.otp.peoplemgmt.repository.AddressRepository;
import hu.otp.peoplemgmt.repository.PersonRepository;
import hu.otp.peoplemgmt.service.impl.AddressServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AddressServiceTest {

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

    private AddressDTO createAddressDTO(String city, String personId) {
        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setCity(city);
        addressDTO.setZipcode("12345");
        addressDTO.setAddressLine("Test utca 123");
        addressDTO.setType(AddressType.CONTINOUS);
        addressDTO.setPersonId(personId);
        return addressDTO;
    }

    @Test
    void testSaveNewAddress() {
        AddressDTO addressDTO = createAddressDTO("Test", "jkovacs");
        Person person = new Person();
        person.setId("jkovacs");

        when(personRepository.existsById("jkovacs")).thenReturn(true);
        when(personRepository.findById("jkovacs")).thenReturn(Optional.of(person));
        when(addressRepository.save(any(Address.class))).thenAnswer(invocation -> {
            Address address = invocation.getArgument(0);
            address.setId(1L);
            return address;
        });

        AddressDTO result = addressService.save(addressDTO);

        assertThat(result.getId()).isEqualTo(1L);
        verify(addressRepository).save(any(Address.class));
    }

    @Test
    void testUpdateExistingAddress() {
        AddressDTO addressDTO = createAddressDTO("Test2", "jkovacs");
        addressDTO.setId(1L);

        Person person = new Person();
        person.setId("jkovacs");

        Address existingAddress = new Address();
        existingAddress.setId(1L);
        existingAddress.setCity("Test");
        existingAddress.setPersonAddress(person);

        when(personRepository.findById("jkovacs")).thenReturn(Optional.of(person));
        when(addressRepository.findById(1L)).thenReturn(Optional.of(existingAddress));
        when(addressRepository.save(any(Address.class))).thenAnswer(invocation -> invocation.getArgument(0));

        AddressDTO result = addressService.save(addressDTO);

        assertThat(result.getCity()).isEqualTo("Test2");
        verify(addressRepository).save(any(Address.class));
    }

    @Test
    void testThrowExceptionIfPersonNotFound() {
        AddressDTO addressDTO = createAddressDTO("Test", "invalid");

        when(personRepository.existsById("invalid")).thenReturn(false);

        assertThrows(IllegalArgumentException.class, () -> addressService.save(addressDTO));
        verify(addressRepository, never()).save(any(Address.class));
    }

    @Test
    void testDelete() {
        Long existingId = 1L;
        when(addressRepository.existsById(existingId)).thenReturn(true);

        addressService.delete(existingId);

        verify(addressRepository).deleteById(existingId);
    }

    @Test
    void testDeleteNotFound() {
        Long nonExistentId = 1L;
        when(addressRepository.existsById(nonExistentId)).thenReturn(false);

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> addressService.delete(nonExistentId));

        assertEquals("Address not found with ID: " + nonExistentId, exception.getMessage());
        verify(addressRepository, never()).deleteById(anyLong());
    }

    @Test
    void testListItems() {
        Person person = new Person();
        person.setId("jkovacs");

        Address address = new Address();
        address.setId(1L);
        address.setCity("Test");
        address.setPersonAddress(person);

        when(addressRepository.findAll()).thenReturn(List.of(address));

        List<AddressDTO> result = addressService.listItems();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getCity()).isEqualTo("Test");
    }

    @Test
    void testGetOneItem() {
        Person person = new Person();
        person.setId("jkovacs");

        Address address = new Address();
        address.setId(1L);
        address.setCity("Test");
        address.setPersonAddress(person);

        when(addressRepository.findById(1L)).thenReturn(Optional.of(address));

        AddressDTO result = addressService.getOneItem(1L);

        assertThat(result.getCity()).isEqualTo("Test");
    }

    @Test
    void testOneItemNotFound() {
        Long nonExistentId = 1L;
        when(addressRepository.findById(nonExistentId)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> addressService.getOneItem(nonExistentId));

        assertEquals("Address not found with ID: " + nonExistentId, exception.getMessage());
    }
}
