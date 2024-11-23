package hu.otp.peoplemgmt.controller;

import hu.otp.peoplemgmt.domain.Address;
import hu.otp.peoplemgmt.domain.dto.AddressDTO;
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
    public ResponseEntity<AddressDTO> addAddress(@RequestBody AddressDTO addressDTO) {
        return ResponseEntity.ok(addressService.save(addressDTO));
    }

    @PutMapping("/update")
    public ResponseEntity<AddressDTO> updateAddress(@RequestBody AddressDTO addressDto) {
        return ResponseEntity.ok(addressService.save(addressDto));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteAddress(@PathVariable Long id) {
        addressService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/list-items")
    public ResponseEntity<List<AddressDTO>> listAddresses() {
        return ResponseEntity.ok(addressService.listItems());
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<AddressDTO> findOne(@PathVariable Long id) {
        return ResponseEntity.ok(addressService.getOneItem(id));
    }

}
