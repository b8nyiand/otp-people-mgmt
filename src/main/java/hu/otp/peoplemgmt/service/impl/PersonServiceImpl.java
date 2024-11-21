package hu.otp.peoplemgmt.service.impl;

import hu.otp.peoplemgmt.domain.Person;
import hu.otp.peoplemgmt.repository.PersonRepository;
import hu.otp.peoplemgmt.service.PersonService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class PersonServiceImpl implements PersonService {

    @Autowired
    private PersonRepository personRepository;

    @Override
    public Person save(Person person) {
        return personRepository.save(person);
    }

    @Override
    public void delete(Long id) {
        personRepository.deleteById(id);
    }

    @Override
    public List<Person> listItems() {
        return personRepository.findAll();
    }

    @Override
    public Person getOneItem(Long id) {
        if (personRepository.findById(id).isPresent()) {
            return personRepository.findById(id).get();
        }
        return null;
    }

}
