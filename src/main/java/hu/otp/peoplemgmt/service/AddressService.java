package hu.otp.peoplemgmt.service;

import hu.otp.peoplemgmt.domain.Address;

import java.util.List;

public interface AddressService {

    Address save(Address address);

    void delete(Long id);

    List<Address> listItems();

    Address getOneItem(Long id);

}
