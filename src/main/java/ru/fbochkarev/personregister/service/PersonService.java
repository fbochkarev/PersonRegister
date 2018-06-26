package ru.fbochkarev.personregister.service;

import ru.fbochkarev.personregister.model.Person;

import javax.xml.soap.SOAPException;
import java.util.List;

public interface PersonService {
    public void addPerson(Person person);

    public void updatePerson(Person person);

    public void removePerson(int id);

    public Person getPersonById(int id);

    public List<Person> listPerson();
}
