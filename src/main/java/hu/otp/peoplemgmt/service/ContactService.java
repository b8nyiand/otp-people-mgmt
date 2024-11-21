package hu.otp.peoplemgmt.service;

import hu.otp.peoplemgmt.domain.Contact;

import java.util.List;

public interface ContactService {

    Contact save(Contact contact);

    void delete(Long id);

    List<Contact> listItems();

    Contact getOneItem(Long id);

}
