package hu.otp.peoplemgmt.controller;

import hu.otp.peoplemgmt.domain.Address;
import hu.otp.peoplemgmt.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/address")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @PostMapping("/add")
    public ResponseEntity<Address> addAddress(@RequestBody Address address) {
        return ResponseEntity.ok(addressService.save(address));
    }

    @PutMapping("/update")
    public ResponseEntity<Address> updateAddress(@RequestBody Address address) {
        return ResponseEntity.ok(addressService.save(address));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteAddress(@PathVariable Long id) {
        addressService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/list-items")
    public ResponseEntity<List<Address>> listAddresses() {
        return ResponseEntity.ok(addressService.listItems());
    }

}
