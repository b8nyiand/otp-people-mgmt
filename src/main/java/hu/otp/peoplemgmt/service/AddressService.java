package hu.otp.peoplemgmt.service;

import hu.otp.peoplemgmt.domain.dto.AddressDTO;

import java.util.List;

/**
 * Interface of service of Addresses.
 * @author Andras Nyilas
 */
public interface AddressService {

    /**
     * Creates or updates an Address.
     * @param addressDto the DTO to create or update
     * @return the DTO of the updated Address
     */
    AddressDTO save(AddressDTO addressDto);

    /**
     * Deletes an Address.
     * @param id the ID of an Address.
     */
    void delete(Long id);

    /**
     * Lists all the Addresses.
     * @return a list of Addresses
     */
    List<AddressDTO> listItems();

    /**
     * Retrieves exactly one Address by the given ID.
     * @param id the ID of an Address
     * @return the Address with the desired ID
     */
    AddressDTO getOneItem(Long id);

}
