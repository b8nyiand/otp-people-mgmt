package hu.otp.peoplemgmt.service.impl;

import hu.otp.peoplemgmt.domain.Address;
import hu.otp.peoplemgmt.domain.Person;
import hu.otp.peoplemgmt.domain.dto.AddressDTO;
import hu.otp.peoplemgmt.domain.enumeration.AddressType;
import hu.otp.peoplemgmt.repository.AddressRepository;
import hu.otp.peoplemgmt.repository.PersonRepository;
import hu.otp.peoplemgmt.service.AddressService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

    private static final Logger logger = LogManager.getLogger(AddressServiceImpl.class);

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
        logger.info("Saving address: {}", addressDTO);

        if (addressDTO.getId() == null) {
            validateAddress(addressDTO);
        }

        Address entity = new Address();
        if (addressDTO.getId() != null) {
            entity = addressRepository.findById(addressDTO.getId())
                    .orElseThrow(() -> {
                        logger.error("AddressService::save: Address not found with ID: {}", addressDTO.getId());
                        return new IllegalArgumentException("Address not found with ID: " + addressDTO.getId());
                    });
            addressDTO.setType(entity.getType());
        }

        AddressDTO savedAddress = toDto(addressRepository.save(toEntity(addressDTO, entity)));
        logger.debug("Address saved successfully: {}", savedAddress);

        return savedAddress;
    }

    /**
     * Validates if the address we are trying to save is correct.
     *
     * @param addressDTO the AddressDTO to verify
     */
    private void validateAddress(AddressDTO addressDTO) {
        logger.info("Validating address: {}", addressDTO);

        String personId = addressDTO.getPersonId();
        if (!personRepository.existsById(personId)) {
            logger.error("Person with ID {} does not exist.", personId);
            throw new IllegalArgumentException("Person with ID " + personId + " does not exist.");
        }

        List<Address> existingAddresses = addressRepository.findByPersonAddress_Id(personId);

        boolean hasTemporary = existingAddresses.stream()
                .anyMatch(address -> address.getType() == AddressType.TEMPORARY);

        boolean hasContinous = existingAddresses.stream()
                .anyMatch(address -> address.getType() == AddressType.CONTINOUS);

        if (addressDTO.getType() == AddressType.TEMPORARY && hasTemporary) {
            logger.error("Person with ID {} already has a temporary address.", personId);
            throw new IllegalArgumentException("Person with ID " + personId + " already has a temporary address.");
        }

        if (addressDTO.getType() == AddressType.CONTINOUS && hasContinous) {
            logger.error("Person with ID {} already has a continuous address.", personId);
            throw new IllegalArgumentException("Person with ID " + personId + " already has a continuous address.");
        }

        if (existingAddresses.size() == 2) {
            logger.error("Person with ID {} already has both continuous and temporary addresses.", personId);
            throw new IllegalArgumentException("Person with ID " + personId + " already has both continuous and temporary addresses.");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void delete(Long id) {
        logger.info("Deleting address with ID: {}", id);

        if (!addressRepository.existsById(id)) {
            logger.error("AddressService::delete: Address not found with ID: {}", id);
            throw new EntityNotFoundException("Address not found with ID: " + id);
        }

        addressRepository.deleteById(id);
        logger.debug("Address with ID {} deleted successfully", id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public List<AddressDTO> listItems() {
        logger.info("Fetching all addresses");

        List<AddressDTO> addresses = addressRepository.findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());

        logger.debug("Addresses retrieved: {}", addresses);

        return addresses;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public AddressDTO getOneItem(Long id) {
        logger.info("Fetching address with ID: {}", id);

        AddressDTO address = addressRepository.findById(id)
                .map(this::toDto)
                .orElseThrow(() -> {
                    logger.error("AddressService::getOneItem: Address not found with ID: {}", id);
                    return new EntityNotFoundException("Address not found with ID: " + id);
                });

        logger.debug("Address retrieved: {}", address);

        return address;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public List<AddressDTO> findByPersonId(String personId) {
        logger.info("Fetching addresses for person ID: {}", personId);

        List<AddressDTO> addresses = addressRepository.findByPersonAddress_Id(personId)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());

        logger.debug("Addresses retrieved for person ID {}: {}", personId, addresses);

        return addresses;
    }

    /**
     * Maps an Address entity to DTO.
     *
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
     *
     * @param addressDTO the DTO to map to entity
     * @param entity     the Address entity
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
