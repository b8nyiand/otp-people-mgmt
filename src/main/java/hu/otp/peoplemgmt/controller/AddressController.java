package hu.otp.peoplemgmt.controller;

import hu.otp.peoplemgmt.domain.dto.AddressDTO;
import hu.otp.peoplemgmt.service.AddressService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller of Addresses.
 */
@RestController
@RequestMapping(path = "/address")
public class AddressController {

    private static final Logger logger = LogManager.getLogger(AddressController.class);

    @Autowired
    private AddressService addressService;

    @Operation(summary = "Create an Address", description = "Creates a new Address of a Person from an AddressDTO and saves it.")
    @PostMapping("/add")
    public ResponseEntity<AddressDTO> addAddress(@RequestBody AddressDTO addressDTO) {
        logger.info("Received request to add address: {}", addressDTO);
        AddressDTO savedAddress = addressService.save(addressDTO);
        logger.debug("Address saved: {}", savedAddress);
        return ResponseEntity.ok(savedAddress);
    }

    @Operation(summary = "Update an Address", description = "Updates an Address of a Person from an AddressDTO and saves it.")
    @PutMapping("/update")
    public ResponseEntity<AddressDTO> updateAddress(@RequestBody AddressDTO addressDto) {
        logger.info("Received request to update address: {}", addressDto);
        AddressDTO updatedAddress = addressService.save(addressDto);
        logger.debug("Address updated: {}", updatedAddress);
        return ResponseEntity.ok(updatedAddress);
    }

    @Operation(summary = "Delete an Address", description = "Deletes an Address. Warning: deletion is physical, not logical!")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteAddress(
            @Parameter(description = "ID of the Address to delete", required = true)
            @PathVariable Long id
    ) {
        logger.info("Received request to delete address with ID: {}", id);
        addressService.delete(id);
        logger.debug("Address with ID {} deleted", id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "List Addresses", description = "Gets all Addresses of Persons as a list.")
    @GetMapping("/list-items")
    public ResponseEntity<List<AddressDTO>> listAddresses() {
        logger.info("Received request to list all addresses");
        List<AddressDTO> addresses = addressService.listItems();
        logger.debug("Listed addresses: {}", addresses);
        return ResponseEntity.ok(addresses);
    }

    @Operation(summary = "Get one Address", description = "Gets exactly one Address of a Person by the given ID.")
    @GetMapping("/find/{id}")
    public ResponseEntity<AddressDTO> findOne(
            @Parameter(description = "ID of the Address to get", required = true)
            @PathVariable Long id
    ) {
        logger.info("Received request to find address with ID: {}", id);
        AddressDTO address = addressService.getOneItem(id);
        logger.debug("Found address: {}", address);
        return ResponseEntity.ok(address);
    }

    @Operation(summary = "Find Addresses by Person ID", description = "Retrieves a list of Addresses belonging to a specific Person.")
    @GetMapping("/person/{personId}")
    public ResponseEntity<List<AddressDTO>> getAddressesByPersonId(
            @Parameter(description = "ID of the Person whose Addresses are to be retrieved", required = true)
            @PathVariable String personId
    ) {
        logger.info("Received request to find addresses for person with ID: {}", personId);
        List<AddressDTO> addresses = addressService.findByPersonId(personId);
        logger.debug("Addresses found for person ID {}: {}", personId, addresses);
        return ResponseEntity.ok(addresses);
    }

}
