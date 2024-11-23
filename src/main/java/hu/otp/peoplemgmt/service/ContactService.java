package hu.otp.peoplemgmt.service;

import hu.otp.peoplemgmt.domain.Contact;
import hu.otp.peoplemgmt.domain.dto.ContactDTO;

import java.util.List;

public interface ContactService {

    ContactDTO save(ContactDTO contactDto);

    void delete(Long id);

    List<ContactDTO> listItems();

    ContactDTO getOneItem(Long id);

}
