package hu.otp.peoplemgmt.service.impl;

import hu.otp.peoplemgmt.domain.Address;
import hu.otp.peoplemgmt.domain.Person;
import hu.otp.peoplemgmt.domain.dto.AddressDTO;
import hu.otp.peoplemgmt.domain.enumeration.AddressType;
import hu.otp.peoplemgmt.repository.AddressRepository;
import hu.otp.peoplemgmt.repository.PersonRepository;
import hu.otp.peoplemgmt.service.AddressService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of AddressService.
 * @author Andras Nyilas
 */
@Service
@Transactional
public class AddressServiceImpl implements AddressService {

    /**
     * The repository of Addresses.
     */
    @Autowired
    private AddressRepository addressRepository;

    /**
     * The repository of Persons.
     */
    @Autowired
    private PersonRepository personRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public AddressDTO save(AddressDTO addressDTO) {
        if (addressDTO.getId() == null) {
            validateAddress(addressDTO);
        }

        Address entity = new Address();
        if (addressDTO.getId() != null) {
            entity = addressRepository.findById(addressDTO.getId())
                    .orElseThrow(() -> new IllegalArgumentException("Address not found with ID: " + addressDTO.getId()));
        }

        return toDto(addressRepository.save(toEntity(addressDTO, entity)));
    }

    /**
     * Validates if the address we are trying to save is correct.
     * @param addressDTO the AddressDTO to verify
     */
    private void validateAddress(AddressDTO addressDTO) {
        String personId = addressDTO.getPersonId();
        if (!personRepository.existsById(personId)) {
            throw new IllegalArgumentException("Person with ID " + personId + " does not exist.");
        }

        List<Address> existingAddresses = addressRepository.findByPersonAddress_Id(personId);

        boolean hasTemporary = existingAddresses.stream()
                .anyMatch(address -> address.getType() == AddressType.TEMPORARY);

        boolean hasContinous = existingAddresses.stream()
                .anyMatch(address -> address.getType() == AddressType.CONTINOUS);

        if (addressDTO.getType() == AddressType.TEMPORARY && hasTemporary) {
            throw new IllegalArgumentException("Person with ID " + personId + " already has a temporary address.");
        }

        if (addressDTO.getType() == AddressType.CONTINOUS && hasContinous) {
            throw new IllegalArgumentException("Person with ID " + personId + " already has a continous address.");
        }

        if (existingAddresses.size() == 2) {
            throw new IllegalArgumentException("Person with ID " + personId + " already has both continous and temporary addresses.");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(Long id) {
        addressRepository.deleteById(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<AddressDTO> listItems() {
        return addressRepository.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AddressDTO getOneItem(Long id) {
        return addressRepository.findById(id).map(this::toDto).orElse(null);
    }

    /**
     * Maps an Address entity to DTO.
     * @param address the entity to map to DTO
     * @return a DTO mapped from the Address entity
     */
    private AddressDTO toDto(Address address) {
        AddressDTO dto = new AddressDTO();
        dto.setId(address.getId());
        dto.setZipcode(address.getZipcode());
        dto.setCity(address.getCity());
        dto.setAddressLine(address.getAddressLine());
        dto.setType(address.getType());
        dto.setPersonId(address.getPersonAddress().getId());
        return dto;
    }

    /**
     * Maps an AddressDTO to an Address entity.
     * @param addressDTO the DTO to map to entity
     * @param entity the Address entity
     * @return an entity mapped from the given AddressDTO
     */
    private Address toEntity(AddressDTO addressDTO, Address entity) {
        entity.setZipcode(addressDTO.getZipcode());
        entity.setCity(addressDTO.getCity());
        entity.setAddressLine(addressDTO.getAddressLine());
        entity.setType(addressDTO.getType());

        if (addressDTO.getPersonId() != null) {
            Person person = personRepository.findById(addressDTO.getPersonId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid person ID: " + addressDTO.getPersonId()));
            entity.setPersonAddress(person);
        }
        return entity;
    }

}
