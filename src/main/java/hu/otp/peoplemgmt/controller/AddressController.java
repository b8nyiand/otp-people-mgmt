package hu.otp.peoplemgmt.controller;

import hu.otp.peoplemgmt.domain.dto.AddressDTO;
import hu.otp.peoplemgmt.service.AddressService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller of Addresses.
 * @author Andras Nyilas
 */
@RestController
@RequestMapping(path = "/address")
public class AddressController {

    /**
     * The Address service.
     */
    @Autowired
    private AddressService addressService;

    @Operation(summary = "Create an Address", description = "Creates a new Address of a Person from an AddressDTO and saves it.")
    @PostMapping("/add")
    public ResponseEntity<AddressDTO> addAddress(@RequestBody AddressDTO addressDTO) {
        return ResponseEntity.ok(addressService.save(addressDTO));
    }

    @Operation(summary = "Update an Address", description = "Updates an Address of a Person from an AddressDTO and saves it.")
    @PutMapping("/update")
    public ResponseEntity<AddressDTO> updateAddress(@RequestBody AddressDTO addressDto) {
        return ResponseEntity.ok(addressService.save(addressDto));
    }

    @Operation(summary = "Delete an Address", description = "Deletes an Address. Warning: deletion is physical, not logical!")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteAddress(
            @Parameter(description = "ID of the Address to delete", required = true)
            @PathVariable Long id
    ) {
        addressService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "List Addresses", description = "Gets all Addresses of Persons as a list.")
    @GetMapping("/list-items")
    public ResponseEntity<List<AddressDTO>> listAddresses() {
        return ResponseEntity.ok(addressService.listItems());
    }

    @Operation(summary = "Get one Address", description = "Gets exactly one Address of a Person by the given ID.")
    @GetMapping("/find/{id}")
    public ResponseEntity<AddressDTO> findOne(
            @Parameter(description = "ID of the Address to get", required = true)
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(addressService.getOneItem(id));
    }

}
