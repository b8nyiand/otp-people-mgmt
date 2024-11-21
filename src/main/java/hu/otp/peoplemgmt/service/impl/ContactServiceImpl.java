package hu.otp.peoplemgmt.service.impl;

import hu.otp.peoplemgmt.domain.Contact;
import hu.otp.peoplemgmt.repository.ContactRepository;
import hu.otp.peoplemgmt.service.ContactService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class ContactServiceImpl implements ContactService {

    @Autowired
    private ContactRepository contactRepository;

    public Contact save(Contact contact) {
        return contactRepository.save(contact);
    }

    public void delete(Long id) {
        contactRepository.deleteById(id);
    }

    public List<Contact> listItems() {
        return contactRepository.findAll();
    }

    public Contact getOneItem(Long id) {
        if (contactRepository.findById(id).isPresent()) {
            return contactRepository.findById(id).get();
        }
        return null;
    }

}
