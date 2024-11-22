package hu.otp.peoplemgmt.service;

import hu.otp.peoplemgmt.domain.Address;
import hu.otp.peoplemgmt.domain.dto.AddressDTO;

import java.util.List;

public interface AddressService {

    Address save(AddressDTO addressDto);

    void delete(Long id);

    List<AddressDTO> listItems();

    AddressDTO getOneItem(Long id);

}
