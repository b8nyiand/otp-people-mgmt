package hu.otp.peoplemgmt.service.impl;

import hu.otp.peoplemgmt.domain.Address;
import hu.otp.peoplemgmt.repository.AddressRepository;
import hu.otp.peoplemgmt.service.AddressService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class AddressServiceImpl implements AddressService {

    @Autowired
    private AddressRepository addressRepository;

    @Override
    public Address save(Address address) {
        return addressRepository.save(address);
    }

    @Override
    public void delete(Long id) {
        addressRepository.deleteById(id);
    }

    @Override
    public List<Address> listItems() {
        return addressRepository.findAll();
    }

    @Override
    public Address getOneItem(Long id) {
        if (addressRepository.findById(id).isPresent()) {
            return addressRepository.findById(id).get();
        }
        return null;
    }

}
