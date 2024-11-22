package hu.otp.peoplemgmt.service.impl;

import hu.otp.peoplemgmt.domain.Contact;
import hu.otp.peoplemgmt.domain.Person;
import hu.otp.peoplemgmt.domain.dto.ContactDTO;
import hu.otp.peoplemgmt.repository.ContactRepository;
import hu.otp.peoplemgmt.repository.PersonRepository;
import hu.otp.peoplemgmt.service.ContactService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ContactServiceImpl implements ContactService {

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private PersonRepository personRepository;

    @Override
    public Contact save(ContactDTO contactDto) {
        return contactRepository.save(toEntity(contactDto));
    }

    @Override
    public void delete(Long id) {
        contactRepository.deleteById(id);
    }

    @Override
    public List<ContactDTO> listItems() {
        return contactRepository.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public ContactDTO getOneItem(Long id) {
        if (contactRepository.findById(id).isPresent()) {
            return toDto(contactRepository.findById(id).get());
        }
        return null;
    }

    private ContactDTO toDto(Contact contact) {
        ContactDTO dto = new ContactDTO();
        dto.setId(contact.getId());
        dto.setContactType(contact.getContactType());
        dto.setContactValue(contact.getContactValue());
        dto.setPersonId(contact.getPersonContact().getId());
        return dto;
    }

    private Contact toEntity(ContactDTO contactDto) {
        Contact entity = new Contact();
        entity.setId(contactDto.getId());
        entity.setContactType(contactDto.getContactType());
        entity.setContactValue(contactDto.getContactValue());

        Person person = personRepository.findById(contactDto.getPersonId()).orElseThrow(RuntimeException::new);

        entity.setPersonContact(person);
        return entity;
    }

}
