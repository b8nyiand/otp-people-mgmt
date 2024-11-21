package hu.otp.peoplemgmt.service;

import hu.otp.peoplemgmt.domain.Person;

import java.util.List;

public interface PersonService {

    Person save(Person person);

    void delete(Long id);

    List<Person> listItems();

    Person getOneItem(Long id);

}
